package com.cardcric.infrastructure.config;

import com.cardcric.application.dto.*;
import com.cardcric.application.exception.DuplicateUserException;
import com.cardcric.application.exception.InvalidCredentialsException;
import com.cardcric.application.exception.RefreshTokenException;
import com.cardcric.application.port.input.AuthService;
import com.cardcric.application.port.output.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new DuplicateUserException("Username '" + request.username() + "' is already taken");
        }
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateUserException("Email '" + request.email() + "' is already registered");
        }

        var userId = UUID.randomUUID();
        var hashedPwd = passwordEncoder.encode(request.password());
        var roles = Set.of("USER");

        var accessToken = jwtTokenProvider.generateAccessToken(userId, roles);
        var refreshToken = jwtTokenProvider.generateRefreshToken(userId);
        var refreshExpiry = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationMs());

        var userData = new UserData(userId, request.username(), request.email(), hashedPwd, roles, refreshToken, refreshExpiry);
        userRepository.save(userData);

        return new AuthResponse(accessToken, refreshToken, "Bearer", jwtTokenProvider.getRefreshTokenExpirationMs(), userId, request.username(), roles);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        var userData = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), userData.password())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        var accessToken = jwtTokenProvider.generateAccessToken(userData.id(), userData.roles());
        var refreshToken = jwtTokenProvider.generateRefreshToken(userData.id());
        var refreshExpiry = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationMs());

        var updatedUser = new UserData(
            userData.id(), userData.username(), userData.email(), userData.password(),
            userData.roles(), refreshToken, refreshExpiry
        );
        userRepository.save(updatedUser);

        return new AuthResponse(accessToken, refreshToken, "Bearer", jwtTokenProvider.getRefreshTokenExpirationMs(),
            userData.id(), userData.username(), userData.roles());
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        var oldRefreshToken = request.refreshToken();

        if (!jwtTokenProvider.validateToken(oldRefreshToken)) {
            throw new RefreshTokenException("Invalid or expired refresh token");
        }

        var userId = jwtTokenProvider.getUserIdFromToken(oldRefreshToken);
        var userData = userRepository.findById(userId)
            .orElseThrow(() -> new RefreshTokenException("User not found"));

        if (!oldRefreshToken.equals(userData.refreshToken())) {
            throw new RefreshTokenException("Refresh token has been revoked");
        }

        if (userData.refreshTokenExpiryDate() != null && userData.refreshTokenExpiryDate().isBefore(Instant.now())) {
            throw new RefreshTokenException("Refresh token has expired");
        }

        var roles = userData.roles();
        var newAccessToken = jwtTokenProvider.generateAccessToken(userId, roles);
        var newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);
        var newExpiry = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationMs());

        var updatedUser = new UserData(
            userData.id(), userData.username(), userData.email(), userData.password(),
            roles, newRefreshToken, newExpiry
        );
        userRepository.save(updatedUser);

        return new AuthResponse(newAccessToken, newRefreshToken, "Bearer",
            jwtTokenProvider.getRefreshTokenExpirationMs(), userId, userData.username(), roles);
    }

    @Override
    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return;
        }

        userRepository.findByRefreshToken(refreshToken).ifPresent(userData -> {
            var updatedUser = new UserData(
                userData.id(), userData.username(), userData.email(), userData.password(),
                userData.roles(), null, null
            );
            userRepository.save(updatedUser);
        });
    }
}

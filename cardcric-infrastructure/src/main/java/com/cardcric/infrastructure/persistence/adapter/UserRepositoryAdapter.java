package com.cardcric.infrastructure.persistence.adapter;

import com.cardcric.application.dto.UserData;
import com.cardcric.application.port.output.UserRepository;
import com.cardcric.infrastructure.persistence.entity.Role;
import com.cardcric.infrastructure.persistence.entity.UserEntity;
import com.cardcric.infrastructure.persistence.spring.SpringDataUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springRepo;

    public UserRepositoryAdapter(SpringDataUserRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Optional<UserData> findByUsername(String username) {
        return springRepo.findByUsername(username).map(this::toUserData);
    }

    @Override
    public Optional<UserData> findByEmail(String email) {
        return springRepo.findByEmail(email).map(this::toUserData);
    }

    @Override
    public Optional<UserData> findById(UUID id) {
        return springRepo.findById(id).map(this::toUserData);
    }

    @Override
    public Optional<UserData> findByRefreshToken(String refreshToken) {
        return springRepo.findByRefreshToken(refreshToken).map(this::toUserData);
    }

    @Override
    public UserData save(UserData data) {
        var entity = springRepo.findById(data.id())
            .orElseGet(() -> new UserEntity(data.id(), data.username(), data.email(), data.password(), Set.of()));

        entity.setUsername(data.username());
        entity.setEmail(data.email());
        entity.setPassword(data.password());
        entity.setRoles(data.roles().stream()
            .map(Role::valueOf)
            .collect(Collectors.toSet()));
        entity.setRefreshToken(data.refreshToken());
        entity.setRefreshTokenExpiryDate(data.refreshTokenExpiryDate());

        var saved = springRepo.save(entity);
        return toUserData(saved);
    }

    private UserData toUserData(UserEntity entity) {
        return new UserData(
            entity.getId(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getRoles().stream().map(Enum::name).collect(Collectors.toSet()),
            entity.getRefreshToken(),
            entity.getRefreshTokenExpiryDate()
        );
    }
}

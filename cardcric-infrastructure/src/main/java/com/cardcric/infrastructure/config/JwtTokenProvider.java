package com.cardcric.infrastructure.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecret;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtTokenProvider(
        @Value("${app.jwt.secret}") String jwtSecret,
        @Value("${app.jwt.access-token-expiration}") long accessTokenExpirationMs,
        @Value("${app.jwt.refresh-token-expiration}") long refreshTokenExpirationMs
    ) {
        this.jwtSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String generateAccessToken(UUID userId, Set<String> roles) {
        var now = new Date();
        var expiry = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
            .subject(userId.toString())
            .claim("roles", roles)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(jwtSecret)
            .compact();
    }

    public String generateRefreshToken(UUID userId) {
        var now = new Date();
        var expiry = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder()
            .subject(userId.toString())
            .claim("type", "refresh")
            .issuedAt(now)
            .expiration(expiry)
            .signWith(jwtSecret)
            .compact();
    }

    public UUID getUserIdFromToken(String token) {
        var claims = getClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public long getRefreshTokenExpirationMs() {
        return refreshTokenExpirationMs;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(jwtSecret)
            .build()
            .parseSignedClaims(token)
            .getBody();
    }
}

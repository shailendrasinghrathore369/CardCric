package com.cardcric.application.dto;

import java.util.Set;
import java.util.UUID;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn,
    UUID userId,
    String username,
    Set<String> roles
) {}

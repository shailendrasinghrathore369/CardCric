package com.cardcric.application.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserData(
    UUID id,
    String username,
    String email,
    String password,
    Set<String> roles,
    String refreshToken,
    Instant refreshTokenExpiryDate
) {}

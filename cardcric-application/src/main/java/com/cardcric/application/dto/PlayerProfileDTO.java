package com.cardcric.application.dto;

import java.util.List;
import java.util.UUID;

public record PlayerProfileDTO(
    UUID id,
    String username,
    String displayName,
    List<UUID> cardIds
) {}

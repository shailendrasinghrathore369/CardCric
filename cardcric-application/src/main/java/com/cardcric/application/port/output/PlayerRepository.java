package com.cardcric.application.port.output;

import com.cardcric.application.dto.PlayerProfileDTO;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    PlayerProfileDTO save(PlayerProfileDTO player);
    Optional<PlayerProfileDTO> findById(UUID playerId);
}

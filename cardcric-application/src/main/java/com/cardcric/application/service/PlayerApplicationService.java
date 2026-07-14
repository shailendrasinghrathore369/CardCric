package com.cardcric.application.service;

import com.cardcric.application.command.RegisterPlayerCommand;
import com.cardcric.application.dto.PlayerProfileDTO;
import com.cardcric.application.port.input.PlayerUseCase;
import com.cardcric.application.port.output.PlayerRepository;
import com.cardcric.application.query.GetPlayerProfileQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
public class PlayerApplicationService implements PlayerUseCase {

    private final PlayerRepository playerRepository;

    public PlayerApplicationService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerProfileDTO registerPlayer(RegisterPlayerCommand command) {
        var profile = new PlayerProfileDTO(
            UUID.randomUUID(),
            command.username(),
            command.displayName(),
            Collections.emptyList()
        );
        return playerRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerProfileDTO getPlayerProfile(GetPlayerProfileQuery query) {
        return playerRepository.findById(query.playerId())
            .orElseThrow(() -> new IllegalArgumentException("Player not found: " + query.playerId()));
    }
}

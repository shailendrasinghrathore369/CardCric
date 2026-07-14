package com.cardcric.infrastructure.persistence.adapter;

import com.cardcric.application.dto.PlayerProfileDTO;
import com.cardcric.application.port.output.PlayerRepository;
import com.cardcric.infrastructure.persistence.entity.PlayerEntity;
import com.cardcric.infrastructure.persistence.spring.SpringDataPlayerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerRepositoryAdapter implements PlayerRepository {

    private final SpringDataPlayerRepository springRepo;

    public PlayerRepositoryAdapter(SpringDataPlayerRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public PlayerProfileDTO save(PlayerProfileDTO player) {
        var entity = new PlayerEntity(player.id(), player.username(), player.displayName(), player.cardIds());
        var saved = springRepo.save(entity);
        return new PlayerProfileDTO(saved.getId(), saved.getUsername(), saved.getDisplayName(), saved.getCardIds());
    }

    @Override
    public Optional<PlayerProfileDTO> findById(UUID playerId) {
        return springRepo.findById(playerId)
            .map(e -> new PlayerProfileDTO(e.getId(), e.getUsername(), e.getDisplayName(), e.getCardIds()));
    }
}

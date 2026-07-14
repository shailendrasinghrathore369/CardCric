package com.cardcric.infrastructure.persistence.adapter;

import com.cardcric.domain.model.CricketCard;
import com.cardcric.domain.port.CardRepository;
import com.cardcric.infrastructure.persistence.entity.CricketCardEntity;
import com.cardcric.infrastructure.persistence.spring.SpringDataCardRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CardRepositoryAdapter implements CardRepository {

    private final SpringDataCardRepository springRepo;

    public CardRepositoryAdapter(SpringDataCardRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public CricketCard save(CricketCard card) {
        return springRepo.save(CricketCardEntity.from(card)).toDomain();
    }

    @Override
    public Optional<CricketCard> findById(UUID cardId) {
        return springRepo.findById(cardId).map(CricketCardEntity::toDomain);
    }

    @Override
    public List<CricketCard> findAllById(List<UUID> cardIds) {
        return springRepo.findAllById(cardIds).stream()
            .map(CricketCardEntity::toDomain)
            .toList();
    }

    @Override
    public List<CricketCard> findAll() {
        return springRepo.findAll().stream()
            .map(CricketCardEntity::toDomain)
            .toList();
    }
}

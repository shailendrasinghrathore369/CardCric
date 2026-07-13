package com.cardcric.domain.port;

import com.cardcric.domain.model.CricketCard;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository {
    Optional<CricketCard> findById(UUID cardId);
    List<CricketCard> findAllById(List<UUID> cardIds);
    List<CricketCard> findAll();
}

package com.cardcric.application.service;

import com.cardcric.application.command.CreateCardCommand;
import com.cardcric.application.dto.CardDTO;
import com.cardcric.application.port.input.CardUseCase;
import com.cardcric.application.query.GetCardQuery;
import com.cardcric.application.query.ListCardsQuery;
import com.cardcric.domain.model.CricketCard;
import com.cardcric.domain.port.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CardApplicationService implements CardUseCase {

    private final CardRepository cardRepository;

    public CardApplicationService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardDTO createCard(CreateCardCommand command) {
        var card = new CricketCard(
            UUID.randomUUID(),
            command.name(),
            command.role(),
            command.battingAverage(),
            command.bowlingAverage(),
            command.strikeRate(),
            command.economyRate(),
            command.fieldingRating(),
            command.imageUrl()
        );
        var saved = cardRepository.save(card);
        return CardDTO.from(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CardDTO getCard(GetCardQuery query) {
        return cardRepository.findById(query.cardId())
            .map(CardDTO::from)
            .orElseThrow(() -> new IllegalArgumentException("Card not found: " + query.cardId()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardDTO> listCards(ListCardsQuery query) {
        return cardRepository.findAll().stream()
            .map(CardDTO::from)
            .toList();
    }
}

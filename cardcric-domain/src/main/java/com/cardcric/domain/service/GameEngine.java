package com.cardcric.domain.service;

import com.cardcric.domain.enums.MatchPhase;
import com.cardcric.domain.event.BallBowledEvent;
import com.cardcric.domain.event.DomainEvent;
import com.cardcric.domain.event.MatchStartedEvent;
import com.cardcric.domain.model.CricketCard;
import com.cardcric.domain.valueobject.*;
import java.util.List;
import java.util.UUID;

public interface GameEngine {
    BowlResult bowl(
        MatchState currentState,
        CricketCard bowlerCard,
        CricketCard batsmanCard
    );

    MatchState applyEvent(MatchState state, DomainEvent event);

    MatchState initialize(MatchStartedEvent event);

    record BowlResult(BallBowledEvent event, MatchState newState) {}
}

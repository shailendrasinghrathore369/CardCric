package com.cardcric.application.service;

import com.cardcric.application.command.BowlBallCommand;
import com.cardcric.application.command.CreateMatchCommand;
import com.cardcric.application.command.ProcessTossCommand;
import com.cardcric.application.dto.BowlBallResultDTO;
import com.cardcric.application.dto.MatchStateDTO;
import com.cardcric.application.port.input.MatchUseCase;
import com.cardcric.application.port.output.MatchEventBroadcaster;
import com.cardcric.application.query.GetMatchStateQuery;
import com.cardcric.domain.event.MatchStartedEvent;
import com.cardcric.domain.event.TossResultEvent;
import com.cardcric.domain.port.CardRepository;
import com.cardcric.domain.port.MatchEventStore;
import com.cardcric.domain.service.GameEngine;
import com.cardcric.domain.valueobject.MatchState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class MatchApplicationService implements MatchUseCase {

    private final GameEngine gameEngine;
    private final CardRepository cardRepository;
    private final MatchEventStore eventStore;
    private final MatchEventBroadcaster broadcaster;

    public MatchApplicationService(GameEngine gameEngine, CardRepository cardRepository, MatchEventStore eventStore, MatchEventBroadcaster broadcaster) {
        this.gameEngine = gameEngine;
        this.cardRepository = cardRepository;
        this.eventStore = eventStore;
        this.broadcaster = broadcaster;
    }

    @Override
    public MatchStateDTO createMatch(CreateMatchCommand command) {
        var matchId = UUID.randomUUID();
        var event = new MatchStartedEvent(
            UUID.randomUUID(),
            matchId,
            Instant.now(),
            command.playerOneId(),
            command.playerTwoId(),
            command.playerOneCardIds(),
            command.playerTwoCardIds(),
            command.totalOvers()
        );
        eventStore.append(event);
        var initialState = gameEngine.initialize(event);
        var dto = MatchStateDTO.from(initialState);
        broadcaster.broadcastMatchState(matchId, dto);
        return dto;
    }

    @Override
    public MatchStateDTO processToss(ProcessTossCommand command) {
        var state = rebuildState(command.matchId());
        var event = new TossResultEvent(
            UUID.randomUUID(),
            command.matchId(),
            Instant.now(),
            command.tossWinnerId(),
            command.electedToBat()
        );
        eventStore.append(event);
        var newState = gameEngine.applyEvent(state, event);
        var dto = MatchStateDTO.from(newState);
        broadcaster.broadcastMatchState(command.matchId(), dto);
        return dto;
    }

    @Override
    public BowlBallResultDTO bowlBall(BowlBallCommand command) {
        var state = rebuildState(command.matchId());
        var bowlerCard = cardRepository.findById(command.bowlerCardId())
            .orElseThrow(() -> new IllegalArgumentException("Bowler card not found: " + command.bowlerCardId()));
        var batsmanCard = cardRepository.findById(command.batsmanCardId())
            .orElseThrow(() -> new IllegalArgumentException("Batsman card not found: " + command.batsmanCardId()));
        var result = gameEngine.bowl(state, bowlerCard, batsmanCard);
        eventStore.append(result.event());
        var dto = BowlBallResultDTO.from(result);
        broadcaster.broadcastBallResult(command.matchId(), dto);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public MatchStateDTO getMatchState(GetMatchStateQuery query) {
        var state = rebuildState(query.matchId());
        return MatchStateDTO.from(state);
    }

    private MatchState rebuildState(UUID matchId) {
        var events = eventStore.findByAggregateId(matchId);
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Match not found: " + matchId);
        }
        if (!(events.getFirst() instanceof MatchStartedEvent startedEvent)) {
            throw new IllegalStateException("First event must be MatchStartedEvent");
        }
        var state = gameEngine.initialize(startedEvent);
        for (int i = 1; i < events.size(); i++) {
            state = gameEngine.applyEvent(state, events.get(i));
        }
        return state;
    }
}

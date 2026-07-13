package com.cardcric.domain.service;

import com.cardcric.domain.enums.MatchPhase;
import com.cardcric.domain.event.*;
import com.cardcric.domain.model.CricketCard;
import com.cardcric.domain.valueobject.*;
import java.time.Instant;
import java.util.*;

public class CricketGameEngine implements GameEngine {
    private final Random random;

    public CricketGameEngine(Random random) {
        this.random = random;
    }

    @Override
    public BowlResult bowl(MatchState state, CricketCard bowler, CricketCard batsman) {
        double batsmanStrength = (batsman.battingAverage() / 100.0)
                               * (batsman.strikeRate() / 200.0);
        double bowlerStrength = ((100.0 - bowler.bowlingAverage()) / 100.0)
                              * ((30.0 - bowler.economyRate()) / 30.0);
        double advantage = Math.clamp(batsmanStrength - bowlerStrength + 0.5, 0.0, 1.0);

        double roll = random.nextDouble();
        BallResult result;

        if (roll < 0.05 + advantage * 0.03) {
            result = BallResult.wicket(pickWicketType());
        } else if (roll < 0.08 + advantage * 0.06) {
            result = BallResult.six();
        } else if (roll < 0.20 + advantage * 0.15) {
            result = BallResult.boundary();
        } else if (roll < 0.22 + advantage * 0.08) {
            result = new BallResult(3, false, null);
        } else if (roll < 0.30 + advantage * 0.10) {
            result = new BallResult(2, false, null);
        } else if (roll < 0.55 + advantage * 0.15) {
            result = new BallResult(1, false, null);
        } else {
            result = BallResult.dotBall();
        }

        int overNumber = state.overs().overs() + 1;
        int ballNumber = state.overs().ballsInCurrentOver() + 1;
        boolean overComplete = ballNumber >= 6;

        BallBowledEvent event = new BallBowledEvent(
            UUID.randomUUID(),
            state.matchId(),
            Instant.now(),
            batsman.id(),
            bowler.id(),
            result,
            overNumber,
            ballNumber
        );

        Score newScore = result.isWicket()
            ? state.score().addWicket().addRuns(result.runsScored())
            : state.score().addRuns(result.runsScored());

        Overs newOvers = new Overs(state.overs().totalBalls() + 1);

        boolean strikeRotation = (result.runsScored() % 2 == 1) || overComplete;
        UUID newStriker = strikeRotation ? state.nonStrikerCardId() : state.strikerCardId();
        UUID newNonStriker = strikeRotation ? state.strikerCardId() : state.nonStrikerCardId();

        MatchPhase nextPhase = state.phase();
        if (state.isInningsOver()) {
            nextPhase = state.phase() == MatchPhase.INNINGS_ONE
                ? MatchPhase.INNINGS_TWO
                : MatchPhase.COMPLETED;
        }

        MatchState newState = new MatchState(
            state.matchId(),
            nextPhase,
            state.battingPlayerId(),
            state.bowlingPlayerId(),
            newScore,
            newOvers,
            newStriker,
            newNonStriker,
            state.bowlerCardId(),
            state.totalOvers()
        );

        return new BowlResult(event, newState);
    }

    @Override
    public MatchState applyEvent(MatchState state, DomainEvent event) {
        return switch (event) {
            case BallBowledEvent e -> apply(e, state);
            case TossResultEvent e -> apply(e, state);
            case InningsCompletedEvent e -> apply(e, state);
            case MatchStartedEvent e -> state;
            case MatchCompletedEvent e -> new MatchState(
                state.matchId(), MatchPhase.COMPLETED,
                state.battingPlayerId(), state.bowlingPlayerId(),
                state.score(), state.overs(),
                state.strikerCardId(), state.nonStrikerCardId(),
                state.bowlerCardId(), state.totalOvers()
            );
            default -> throw new IllegalArgumentException("Unknown event: " + event.eventType());
        };
    }

    @Override
    public MatchState initialize(MatchStartedEvent event) {
        return new MatchState(
            event.aggregateId(),
            MatchPhase.TOSS,
            null, null,
            new Score(0, 0),
            new Overs(0),
            null, null, null,
            event.totalOvers()
        );
    }

    private MatchState apply(BallBowledEvent e, MatchState state) {
        boolean oddRuns = e.result().runsScored() % 2 == 1;
        boolean overComplete = e.ballNumber() == 0 && e.overNumber() > state.overs().overs();

        Score newScore = e.result().isWicket()
            ? state.score().addWicket().addRuns(e.result().runsScored())
            : state.score().addRuns(e.result().runsScored());

        UUID newStriker = (oddRuns || overComplete)
            ? state.nonStrikerCardId()
            : state.strikerCardId();
        UUID newNonStriker = (oddRuns || overComplete)
            ? state.strikerCardId()
            : state.nonStrikerCardId();

        return new MatchState(
            state.matchId(), state.phase(),
            state.battingPlayerId(), state.bowlingPlayerId(),
            newScore, new Overs(state.overs().totalBalls() + 1),
            newStriker, newNonStriker, state.bowlerCardId(),
            state.totalOvers()
        );
    }

    private MatchState apply(TossResultEvent e, MatchState state) {
        UUID battingPlayerId = e.electedToBat() ? e.tossWinnerId() : opponentOf(state, e.tossWinnerId());
        UUID bowlingPlayerId = e.electedToBat() ? opponentOf(state, e.tossWinnerId()) : e.tossWinnerId();
        return new MatchState(
            state.matchId(), MatchPhase.INNINGS_ONE,
            battingPlayerId, bowlingPlayerId,
            new Score(0, 0), new Overs(0),
            null, null, null,
            state.totalOvers()
        );
    }

    private MatchState apply(InningsCompletedEvent e, MatchState state) {
        return new MatchState(
            state.matchId(), e.nextPhase(),
            state.battingPlayerId(), state.bowlingPlayerId(),
            state.score(), state.overs(),
            state.strikerCardId(), state.nonStrikerCardId(),
            state.bowlerCardId(), state.totalOvers()
        );
    }

    private BallResult.WicketType pickWicketType() {
        BallResult.WicketType[] types = BallResult.WicketType.values();
        return types[random.nextInt(types.length)];
    }

    private UUID opponentOf(MatchState state, UUID playerId) {
        return state.battingPlayerId() == null
            ? null
            : state.battingPlayerId().equals(playerId)
                ? state.bowlingPlayerId()
                : state.battingPlayerId();
    }
}

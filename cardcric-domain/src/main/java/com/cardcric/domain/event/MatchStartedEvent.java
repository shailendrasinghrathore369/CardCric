package com.cardcric.domain.event;

import com.cardcric.domain.enums.MatchPhase;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record MatchStartedEvent(
    UUID eventId,
    UUID aggregateId,
    Instant occurredAt,
    UUID playerOneId,
    UUID playerTwoId,
    List<UUID> playerOneCardIds,
    List<UUID> playerTwoCardIds,
    int totalOvers
) implements DomainEvent {
    public static final String EVENT_TYPE = "MATCH_STARTED";

    public MatchStartedEvent {
        if (eventId == null || aggregateId == null || occurredAt == null) {
            throw new IllegalArgumentException("eventId, aggregateId, and occurredAt must not be null");
        }
        if (totalOvers <= 0) {
            throw new IllegalArgumentException("totalOvers must be positive");
        }
        if (playerOneCardIds == null || playerTwoCardIds == null) {
            throw new IllegalArgumentException("Card lists must not be null");
        }
        if (playerOneCardIds.isEmpty() || playerTwoCardIds.isEmpty()) {
            throw new IllegalArgumentException("Both players must have cards");
        }
    }

    public UUID opponentOf(UUID playerId) {
        if (playerId.equals(playerOneId)) return playerTwoId;
        if (playerId.equals(playerTwoId)) return playerOneId;
        throw new IllegalArgumentException("Player " + playerId + " is not in this match");
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

    public MatchPhase initialPhase() {
        return MatchPhase.TOSS;
    }
}

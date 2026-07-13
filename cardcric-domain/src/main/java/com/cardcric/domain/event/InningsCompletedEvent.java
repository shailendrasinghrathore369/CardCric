package com.cardcric.domain.event;

import com.cardcric.domain.enums.MatchPhase;
import java.time.Instant;
import java.util.UUID;

public record InningsCompletedEvent(
    UUID eventId,
    UUID aggregateId,
    Instant occurredAt,
    int inningsNumber,
    int totalRuns,
    int totalWickets,
    int totalBalls,
    MatchPhase nextPhase
) implements DomainEvent {
    public static final String EVENT_TYPE = "INNINGS_COMPLETED";

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}

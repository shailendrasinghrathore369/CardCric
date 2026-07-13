package com.cardcric.domain.event;

import com.cardcric.domain.valueobject.BallResult;
import java.time.Instant;
import java.util.UUID;

public record BallBowledEvent(
    UUID eventId,
    UUID aggregateId,
    Instant occurredAt,
    UUID batsmanCardId,
    UUID bowlerCardId,
    BallResult result,
    int overNumber,
    int ballNumber
) implements DomainEvent {
    public static final String EVENT_TYPE = "BALL_BOWLED";

    public BallBowledEvent {
        if (eventId == null || aggregateId == null || occurredAt == null) {
            throw new IllegalArgumentException("eventId, aggregateId, and occurredAt must not be null");
        }
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}

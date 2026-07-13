package com.cardcric.domain.event;

import java.time.Instant;
import java.util.UUID;

public record TossResultEvent(
    UUID eventId,
    UUID aggregateId,
    Instant occurredAt,
    UUID tossWinnerId,
    boolean electedToBat
) implements DomainEvent {
    public static final String EVENT_TYPE = "TOSS_RESULT";

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}

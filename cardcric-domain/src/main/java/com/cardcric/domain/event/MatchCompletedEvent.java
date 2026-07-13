package com.cardcric.domain.event;

import java.time.Instant;
import java.util.UUID;

public record MatchCompletedEvent(
    UUID eventId,
    UUID aggregateId,
    Instant occurredAt,
    UUID winnerId,
    UUID loserId,
    String resultDescription
) implements DomainEvent {
    public static final String EVENT_TYPE = "MATCH_COMPLETED";

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}

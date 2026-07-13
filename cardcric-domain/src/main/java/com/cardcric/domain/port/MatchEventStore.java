package com.cardcric.domain.port;

import com.cardcric.domain.event.DomainEvent;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchEventStore {
    void append(DomainEvent event);
    List<DomainEvent> findByAggregateId(UUID aggregateId);
    Optional<DomainEvent> findById(UUID eventId);
    long countByAggregateId(UUID aggregateId);
}

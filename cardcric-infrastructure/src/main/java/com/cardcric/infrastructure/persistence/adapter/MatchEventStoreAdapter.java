package com.cardcric.infrastructure.persistence.adapter;

import com.cardcric.domain.event.DomainEvent;
import com.cardcric.domain.port.MatchEventStore;
import com.cardcric.infrastructure.persistence.entity.DomainEventEntity;
import com.cardcric.infrastructure.persistence.spring.SpringDataEventStoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MatchEventStoreAdapter implements MatchEventStore {

    private final SpringDataEventStoreRepository springRepo;

    public MatchEventStoreAdapter(SpringDataEventStoreRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public void append(DomainEvent event) {
        springRepo.save(DomainEventEntity.from(event));
    }

    @Override
    public List<DomainEvent> findByAggregateId(UUID aggregateId) {
        return springRepo.findByAggregateIdOrderByOccurredAtAsc(aggregateId).stream()
            .map(DomainEventEntity::toDomain)
            .toList();
    }

    @Override
    public Optional<DomainEvent> findById(UUID eventId) {
        return springRepo.findById(eventId).map(DomainEventEntity::toDomain);
    }

    @Override
    public long countByAggregateId(UUID aggregateId) {
        return springRepo.countByAggregateId(aggregateId);
    }
}

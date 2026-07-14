package com.cardcric.infrastructure.persistence.spring;

import com.cardcric.infrastructure.persistence.entity.DomainEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataEventStoreRepository extends JpaRepository<DomainEventEntity, UUID> {
    List<DomainEventEntity> findByAggregateIdOrderByOccurredAtAsc(UUID aggregateId);
    long countByAggregateId(UUID aggregateId);
}

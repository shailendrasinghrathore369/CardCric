package com.cardcric.infrastructure.persistence.entity;

import com.cardcric.domain.event.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Entity
@Table(name = "domain_events")
@Index(name = "idx_aggregate_id", columnList = "aggregateId")
public class DomainEventEntity {

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Map<String, Class<? extends DomainEvent>> EVENT_TYPE_MAP = Map.of(
        "MATCH_STARTED", MatchStartedEvent.class,
        "TOSS_RESULT", TossResultEvent.class,
        "BALL_BOWLED", BallBowledEvent.class,
        "INNINGS_COMPLETED", InningsCompletedEvent.class,
        "MATCH_COMPLETED", MatchCompletedEvent.class
    );

    @Id
    private UUID eventId;

    @Column(nullable = false)
    private UUID aggregateId;

    @Column(nullable = false)
    private Instant occurredAt;

    @Column(nullable = false)
    private String eventType;

    @Lob
    @Column(nullable = false)
    private String data;

    protected DomainEventEntity() {}

    public DomainEventEntity(UUID eventId, UUID aggregateId, Instant occurredAt, String eventType, String data) {
        this.eventId = eventId;
        this.aggregateId = aggregateId;
        this.occurredAt = occurredAt;
        this.eventType = eventType;
        this.data = data;
    }

    public static DomainEventEntity from(DomainEvent event) {
        try {
            return new DomainEventEntity(
                event.eventId(),
                event.aggregateId(),
                event.occurredAt(),
                event.eventType(),
                MAPPER.writeValueAsString(event)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize domain event", e);
        }
    }

    public DomainEvent toDomain() {
        try {
            var eventClass = EVENT_TYPE_MAP.get(eventType);
            if (eventClass == null) {
                throw new IllegalArgumentException("Unknown event type: " + eventType);
            }
            return MAPPER.readValue(data, eventClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize domain event", e);
        }
    }

    public UUID getEventId() { return eventId; }
    public UUID getAggregateId() { return aggregateId; }
    public Instant getOccurredAt() { return occurredAt; }
    public String getEventType() { return eventType; }
    public String getData() { return data; }
}

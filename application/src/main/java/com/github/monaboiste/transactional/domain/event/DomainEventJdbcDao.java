package com.github.monaboiste.transactional.domain.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
class DomainEventJdbcDao implements DomainEventStore {

    private final NamedParameterJdbcTemplate jdbc;
    private final PayloadSerializer serializer;

    @Override
    public void save(final DomainEvent event) {
        log.info("Persisting event {}", event);
        String statement = """
                insert into events(event_id, occurred_at, name, aggregate_id, aggregate_type, payload)
                values (:eventId, :occurredAt, :name, :aggregateId, :aggregateType, :payload)
                """;
        var params = eventToParams(event);
        jdbc.update(statement, params);
    }

    @Override
    public void saveAll(final List<DomainEvent> events) {
        log.info("Persisting events {}", events);
        String statement = """
                insert into events(event_id, occurred_at, name, aggregate_id, aggregate_type, payload)
                values (:eventId, :occurredAt, :name, :aggregateId, :aggregateType, :payload)
                """;
        @SuppressWarnings("unchecked")
        Map<String, ?>[] batches = events.stream()
                .map(this::eventToParams)
                .toArray(Map[]::new);
        jdbc.batchUpdate(statement, batches);
    }

    private Map<String, ? extends Serializable> eventToParams(final DomainEvent event) {
        return Map.of(
                "eventId", event.eventId(),
                "occurredAt", event.occurredAt(),
                "name", event.name(),
                "aggregateId", event.aggregateId(),
                "aggregateType", event.aggregateType(),
                "payload", serialized(event.payload())
        );
    }

    private <T> byte[] serialized(final T payload) {
        return serializer.serialize(payload);
    }
}

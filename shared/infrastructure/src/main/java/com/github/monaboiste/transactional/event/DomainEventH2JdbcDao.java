package com.github.monaboiste.transactional.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
class DomainEventH2JdbcDao implements DomainEventStore {

    private final NamedParameterJdbcTemplate jdbc;
    private final PayloadSerializer serializer;

    @Override
    public <T extends Snapshot> void save(final DomainEvent<T> event) {
        log.info("Persisting event {}", event);
        String statement = """
                insert into events(event_id, occurred_at, name, aggregate_id, aggregate_type, payload)
                values (:eventId, :occurredAt, :name, :aggregateId, :aggregateType, :payload)
                """;
        var params = eventToParams(event);
        jdbc.update(statement, params);
    }

    @Override
    public <T extends Snapshot> void saveAll(final List<DomainEvent<T>> events) {
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

    private Map<String, ? extends Serializable> eventToParams(final DomainEvent<?> event) {
        return Map.of(
                "eventId", event.eventId(),
                "occurredAt", event.occurredAt(),
                "name", event.name(),
                "aggregateId", event.aggregateId(),
                "aggregateType", event.aggregateType(),
                "payload", serialized(event.payload())
        );
    }

    private <T extends Snapshot> byte[] serialized(final T payload) {
        return serializer.serialize(payload);
    }
}

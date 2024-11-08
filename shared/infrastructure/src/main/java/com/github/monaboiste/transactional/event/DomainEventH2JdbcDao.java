package com.github.monaboiste.transactional.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
class DomainEventH2JdbcDao implements DomainEventStore {

    private final NamedParameterJdbcTemplate jdbc;
    private final EventSerializer serializer;

    @Override
    public <T extends Snapshot> void save(final DomainEvent<T> event) {
        log.info("Persisting event {}", event);
        String statement = """
                insert into events(event_id, occurred_at, name, aggregate_id, aggregate_type, event)
                values (:event_id, :occurred_at, :name, :aggregate_id, :aggregate_type, :event)
                """;
        var params = eventToParams.apply(event);
        jdbc.update(statement, params);
    }

    @Override
    public <T extends Snapshot> void saveAll(final List<DomainEvent<T>> events) {
        log.info("Persisting events {}", events);
        String statement = """
                insert into events(event_id, occurred_at, name, aggregate_id, aggregate_type, event)
                values (:event_id, :occurred_at, :name, :aggregate_id, :aggregate_type, :event)
                """;
        @SuppressWarnings("unchecked")
        Map<String, ?>[] batches = events.stream()
                .map(eventToParams)
                .toArray(Map[]::new);
        jdbc.batchUpdate(statement, batches);
    }

    private final Function<DomainEvent<?>, Map<String, ? extends Serializable>> eventToParams =
            event -> Map.of(
                    "event_id", event.eventId(),
                    "occurred_at", event.occurredAt(),
                    "name", event.name(),
                    "aggregate_id", event.aggregateId(),
                    "aggregate_type", event.aggregateType(),
                    "event", serialized(event)
            );

    private <T extends Snapshot> byte[] serialized(final DomainEvent<T> event) {
        return serializer.serialize(event);
    }
}

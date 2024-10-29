package com.github.monaboiste.transactional.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class Hired implements Event {

    private final UUID eventId;
    private final Instant createdAt;
    private final Employee employee;

    public Hired(final Employee employee) {
        this.eventId = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.employee = employee;
    }

    @Override
    public Class<? extends Event> type() {
        return Hired.class;
    }

    @Override
    public UUID eventId() {
        return UUID.randomUUID();
    }

    @Override
    public Instant createdAt() {
        return Instant.now();
    }
}

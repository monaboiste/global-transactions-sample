package com.github.monaboiste.transactional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public final class EmployeeHired implements DomainEvent {
    private static final String NAME = "EmployeeHired";

    private final UUID eventId;
    private final Instant createdAt;
    private final String payload;

    public EmployeeHired(// employee details, really should be an object
                         String payload) {
        this.eventId = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.payload = payload;
    }

    @Override
    public String name() {
        return NAME;
    }
}

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
    private final Employee employee;

    public EmployeeHired(// employee details, it's an entity for now
                         Employee employee) {
        this.eventId = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.employee = employee;
    }

    @Override
    public String name() {
        return NAME;
    }
}

package com.github.monaboiste.transactional.domain.employee;

import com.github.monaboiste.transactional.domain.event.Event;
import com.github.monaboiste.transactional.domain.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@ToString
@EqualsAndHashCode
public class Hired implements DomainEvent {

    private final UUID eventId;
    private final Instant occurredAt;
    private final String aggregateId;
    private final Map<String, String> employee;

    public Hired(final Employee employee) {
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.aggregateId = employee.employeeId().toString();
        this.employee = Map.of(
                "employeeId", employee.employeeId().toString(),
                "firstName", employee.firstName(),
                "lastName", employee.lastName()
        );
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
    public String name() {
        return "EMPLOYEE_HIRED";
    }

    public Instant occurredAt() {
        return Instant.now();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T payload() {
        return (T) employee;
    }

    @Override
    public String aggregateType() {
        return Employee.class.getSimpleName();
    }

    @Override
    public String aggregateId() {
        return aggregateId;
    }
}

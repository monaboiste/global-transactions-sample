package com.github.monaboiste.transactional.domain.employee;

import com.github.monaboiste.transactional.domain.event.DomainEvent;
import com.github.monaboiste.transactional.domain.event.Event;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@ToString
@EqualsAndHashCode
public class EmployeeActivated implements DomainEvent<EmployeeSnapshot> {

    private final UUID eventId;
    private final Instant occurredAt;
    private final String aggregateId;
    private final EmployeeSnapshot employee;

    public EmployeeActivated(final Employee employee) {
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.aggregateId = employee.employeeId().value().toString();
        this.employee = new EmployeeSnapshot(employee);
    }

    @Override
    public Class<? extends Event<EmployeeSnapshot>> type() {
        return EmployeeActivated.class;
    }

    @Override
    public UUID eventId() {
        return UUID.randomUUID();
    }

    @Override
    public String name() {
        return "EMPLOYEE_ACTIVATED";
    }

    public Instant occurredAt() {
        return Instant.now();
    }

    @Override
    public EmployeeSnapshot payload() {
        return employee;
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

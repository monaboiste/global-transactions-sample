package com.github.monaboiste.transactional.employee.event;

import com.github.monaboiste.transactional.employee.Employee;
import com.github.monaboiste.transactional.event.DomainEvent;
import com.github.monaboiste.transactional.event.Event;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
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
        return eventId;
    }

    @Override
    public String name() {
        return "EMPLOYEE_ACTIVATED";
    }

    public Instant occurredAt() {
        return occurredAt;
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

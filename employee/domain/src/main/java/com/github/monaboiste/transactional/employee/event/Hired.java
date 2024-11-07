package com.github.monaboiste.transactional.employee.event;

import com.github.monaboiste.transactional.employee.Employee;
import com.github.monaboiste.transactional.event.DomainEvent;
import com.github.monaboiste.transactional.event.Event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Hired implements DomainEvent<EmployeeSnapshot> {

    private final UUID eventId;
    private final Instant occurredAt;
    private final String aggregateId;
    private final EmployeeSnapshot employee;

    public Hired(final Employee employee) {
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.aggregateId = employee.employeeId().toString();
        this.employee = new EmployeeSnapshot(employee);
    }

    @Override
    public Class<? extends Event<EmployeeSnapshot>> type() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hired hired = (Hired) o;
        return Objects.equals(eventId, hired.eventId) && Objects.equals(occurredAt, hired.occurredAt) && Objects.equals(aggregateId, hired.aggregateId) && Objects.equals(employee, hired.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, occurredAt, aggregateId, employee);
    }

    @Override
    public String toString() {
        return "Hired{" +
               "eventId=" + eventId +
               ", occurredAt=" + occurredAt +
               ", aggregateId='" + aggregateId + '\'' +
               ", employee=" + employee +
               '}';
    }
}

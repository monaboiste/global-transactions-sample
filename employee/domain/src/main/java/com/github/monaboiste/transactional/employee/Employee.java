package com.github.monaboiste.transactional.employee;

import com.github.monaboiste.transactional.domain.Email;
import com.github.monaboiste.transactional.employee.event.EmployeeActivated;
import com.github.monaboiste.transactional.employee.event.EmployeeSnapshot;
import com.github.monaboiste.transactional.employee.event.Hired;
import com.github.monaboiste.transactional.event.DomainEvent;
import com.github.monaboiste.transactional.event.Event;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    @Getter
    private final EmployeeId employeeId;
    @ToString.Exclude
    @SuppressWarnings("squid:S2065")
    private final transient List<DomainEvent<EmployeeSnapshot>> pendingEvents = new ArrayList<>();
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private Email workEmail;
    @Getter
    private boolean active;

    private int version;

    public Employee(EmployeeId employeeId, String firstName, String lastName, Email workEmail) {
        this(employeeId, firstName, lastName, workEmail, false, -1);
    }

    Employee(EmployeeId employeeId, String firstName, String lastName, Email workEmail, boolean active,
             int version) {
        if (employeeId == null || workEmail == null) {
            throw new IllegalArgumentException();
        }
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException(); // todo: domain exception
        }
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workEmail = workEmail;
        this.active = active;
        this.version = version;
    }

    public void hire() {
        appendEvent(new Hired(this));
    }

    private void appendEvent(DomainEvent<EmployeeSnapshot> event) {
        if (event == null) {
            throw new IllegalArgumentException();
        }
        pendingEvents.add(event);
    }

    public void activate() {
        this.active = true;
        appendEvent(new EmployeeActivated(this));
    }

    public void rename(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException(); // todo: domain exception
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Version field to support optimistic locking mechanism
     * - leaked persistence concerns into domain model.
     * TODO: switch back to private-package
     */
    public String version() {
        return String.valueOf(version);
    }

    /**
     * Return a view of pending events.
     *
     * @return a collection of the pending events.
     */
    @UnmodifiableView
    public List<Event<EmployeeSnapshot>> peekPendingEvents() {
        return List.copyOf(pendingEvents);
    }

    /**
     * Increments {@code this} aggregate's version and flushes pending events
     * effectively marking all uncommited changes as commited.
     * <p>
     * <b>
     * Each transaction, which mutates {@code this} state should invoke
     * {@code flushPendingEvents} method before commiting the changes.
     * </b>
     *
     * @return a collection of the pending events (commited changes).
     */
    List<DomainEvent<EmployeeSnapshot>> flushPendingEvents() {
        incrementVersion();
        var returned = new ArrayList<>(pendingEvents);
        pendingEvents.clear();
        return returned;
    }

    private void incrementVersion() {
        version++;
    }
}

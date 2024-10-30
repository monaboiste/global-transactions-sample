package com.github.monaboiste.transactional.domain.employee;

import com.github.monaboiste.transactional.domain.event.DomainEvent;
import com.github.monaboiste.transactional.domain.event.Event;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class Employee {

    private final EmployeeId employeeId;

    private String firstName;
    private String lastName;
    private boolean active;

    @ToString.Exclude
    private final List<DomainEvent> pendingEvents = new ArrayList<>();

    public Employee(EmployeeId employeeId, String firstName, String lastName) {
        this(employeeId, firstName, lastName, false);
    }

    Employee(EmployeeId employeeId, String firstName, String lastName, boolean active) {
        // assert
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;

        appendEvent(new Hired(this));
    }

    public void activate() {
        // append event
        this.active = true;
    }

    public void rename(String firstName, String lastName) {
        // assert
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Return a view of pending events.
     *
     * @return a collection of the pending events.
     */
    @UnmodifiableView
    public List<Event> peek() {
        return List.copyOf(pendingEvents);
    }

    /**
     * Return pending events then clear them.
     *
     * @return a collection of the pending events.
     */
    public List<DomainEvent> flushPendingEvents() {
        var returned = new ArrayList<>(pendingEvents);
        pendingEvents.clear();
        return returned;
    }

    private void appendEvent(DomainEvent event) {
        if (event == null) {
            throw new IllegalArgumentException();
        }
        pendingEvents.add(event);
    }
}

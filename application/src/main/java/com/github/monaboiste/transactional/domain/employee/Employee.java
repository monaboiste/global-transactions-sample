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
    private Email workEmail;
    private boolean active;

    @ToString.Exclude
    @SuppressWarnings("squid:S2065")
    private final transient List<DomainEvent<EmployeeSnapshot>> pendingEvents = new ArrayList<>();

    public Employee(EmployeeId employeeId, String firstName, String lastName, Email workEmail) {
        this(employeeId, firstName, lastName, workEmail, false);
    }

    Employee(EmployeeId employeeId, String firstName, String lastName, Email workEmail, boolean active) {
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

        appendEvent(new Hired(this));
    }

    public void activate() {
        // append event
        this.active = true;
    }

    public void rename(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException(); // todo: domain exception
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Return a view of pending events.
     *
     * @return a collection of the pending events.
     */
    @UnmodifiableView
    public List<Event<EmployeeSnapshot>> peek() {
        return List.copyOf(pendingEvents);
    }

    /**
     * Return pending events then clear them.
     *
     * @return a collection of the pending events.
     */
    public List<DomainEvent<EmployeeSnapshot>> flushPendingEvents() {
        var returned = new ArrayList<>(pendingEvents);
        pendingEvents.clear();
        return returned;
    }

    private void appendEvent(DomainEvent<EmployeeSnapshot> event) {
        if (event == null) {
            throw new IllegalArgumentException();
        }
        pendingEvents.add(event);
    }
}

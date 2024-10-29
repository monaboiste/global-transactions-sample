package com.github.monaboiste.transactional.domain;

import java.util.UUID;

public class Employee {

    private final UUID domainId;
    private final EventPublisher eventPublisher;
    private String firstName;
    private String lastName;
    private boolean active;

    Employee(String firstName, String lastName,
             final EventPublisher eventPublisher) {
        this(UUID.randomUUID(), firstName, lastName, false, eventPublisher);
    }

    Employee(UUID domainId, String firstName, String lastName, boolean active,
             final EventPublisher eventPublisher) {
        this.domainId = domainId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.eventPublisher = eventPublisher;
    }

    public void hire() {
        Hired event = new Hired(this);
        eventPublisher.publish(event);
    }

    public void activate() {
        this.active = true;
        // todo: publish event
    }

    public boolean active() {
        return active;
    }

    public UUID domainId() {
        return domainId;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }
}

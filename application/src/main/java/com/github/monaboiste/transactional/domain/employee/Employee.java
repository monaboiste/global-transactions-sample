package com.github.monaboiste.transactional.domain.employee;

import java.util.UUID;

public class Employee {

    private final UUID domainId;

    private String firstName;
    private String lastName;
    private boolean active;

    Employee(String firstName, String lastName) {
        this(UUID.randomUUID(), firstName, lastName, false);
    }

    Employee(UUID domainId, String firstName, String lastName, boolean active) {
        this.domainId = domainId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }

    public void activate() {
        this.active = true;
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

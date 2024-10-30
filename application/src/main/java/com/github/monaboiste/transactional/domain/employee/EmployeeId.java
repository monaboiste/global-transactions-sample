package com.github.monaboiste.transactional.domain.employee;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public final class EmployeeId {

    private final UUID value;

    public EmployeeId() {
        this(UUID.randomUUID());
    }

    public EmployeeId(UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value().toString();
    }

    public UUID value() {
        return value;
    }
}

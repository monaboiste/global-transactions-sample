package com.github.monaboiste.transactional.employee;

import java.util.UUID;

public record EmployeeId(UUID value) {

    public EmployeeId() {
        this(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

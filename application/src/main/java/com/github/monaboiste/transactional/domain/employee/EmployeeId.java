package com.github.monaboiste.transactional.domain.employee;

import java.util.UUID;

public record EmployeeId(UUID value) {

    @Override
    public String toString() {
        return value().toString();
    }
}

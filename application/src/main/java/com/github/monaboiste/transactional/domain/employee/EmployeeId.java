package com.github.monaboiste.transactional.domain.employee;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
@Getter
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
        return value.toString();
    }
}

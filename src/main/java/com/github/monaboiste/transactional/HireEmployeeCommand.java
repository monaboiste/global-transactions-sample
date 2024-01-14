package com.github.monaboiste.transactional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
final class HireEmployeeCommand implements Command {
    private final UUID employeeId;
    private final String firstName;
    private final String lastName;

    HireEmployeeCommand(String firstName, String lastName) {
        this(UUID.randomUUID(), firstName, lastName);
    }

    HireEmployeeCommand(UUID employeeId, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

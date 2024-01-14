package com.github.monaboiste.transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
final class HireEmployeeCommand implements Command {
    @Setter
    private UUID employeeId;

    private final String firstName;
    private final String lastName;
}

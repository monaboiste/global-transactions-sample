package com.github.monaboiste.transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
public final class NewEmployeeCommand implements Command {
    private final String firstName;
    private final String lastName;

    /**
     * Note: breaking CQS principle for convenience...
     */
    @Setter
    private Long employeeId;
}

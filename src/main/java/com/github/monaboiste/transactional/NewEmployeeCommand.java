package com.github.monaboiste.transactional;

public record NewEmployeeCommand(String firstName, String lastName)
        implements Command {
}

package com.github.monaboiste.transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HireEmployeeCommandHandlerTest {

    @Autowired
    HireEmployeeCommandHandler commandHandler;

    @Autowired
    EmployeeReadRepository employeeReadRepository;

    @Test
    void persisted_employee_should_have_token() {
        UUID employeeId = UUID.nameUUIDFromBytes(new byte[]{0});
        HireEmployeeCommand command = new HireEmployeeCommand(employeeId, "Tester", "Boa");

        commandHandler.handle(command);

        Employee persistedEmployee = employeeReadRepository.getById(employeeId);
        assertThat(persistedEmployee.hasToken()).isTrue();
    }
}
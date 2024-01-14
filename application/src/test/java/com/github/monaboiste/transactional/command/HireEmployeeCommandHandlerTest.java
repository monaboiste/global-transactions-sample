package com.github.monaboiste.transactional.command;

import com.github.monaboiste.transactional.EmployeeReadRepository;
import com.github.monaboiste.transactional.command.HireEmployeeCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

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

        await().atMost(5, SECONDS).until(tokenIsGenerated(employeeId));
    }

    private Callable<Boolean> tokenIsGenerated(UUID employeeId) {
        return () -> {
            var persistedEmployee = employeeReadRepository.getById(employeeId);
            return persistedEmployee.hasToken();
        };
    }
}
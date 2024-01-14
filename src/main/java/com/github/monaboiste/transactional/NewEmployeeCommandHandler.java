package com.github.monaboiste.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class NewEmployeeCommandHandler implements CommandHandler<NewEmployeeCommand> {

    private final EmployeeRepository employeeRepository;

    @Override
    public void handle(NewEmployeeCommand command) {
        log.info("Requested new employee");

        Employee newEmployee = new Employee();
        newEmployee.setDomainId(command.employeeId());
        newEmployee.setFirstName(command.firstName());
        newEmployee.setLastName(command.lastName());

        employeeRepository.save(newEmployee);
    }
}

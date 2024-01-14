package com.github.monaboiste.transactional.command;

import com.github.monaboiste.transactional.event.DomainEventPublisher;
import com.github.monaboiste.transactional.Employee;
import com.github.monaboiste.transactional.event.EmployeeHired;
import com.github.monaboiste.transactional.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
class HireEmployeeCommandHandler implements CommandHandler<HireEmployeeCommand> {

    private final EmployeeRepository employeeRepository;
    private final DomainEventPublisher eventPublisher;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void handle(HireEmployeeCommand command) {
        log.info("Requested new employee");

        Employee newEmployee = new Employee();
        newEmployee.domainId(command.employeeId());
        newEmployee.firstName(command.firstName());
        newEmployee.lastName(command.lastName());

        new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
            employeeRepository.save(newEmployee);
            eventPublisher.publish(new EmployeeHired(newEmployee));
        });
    }
}

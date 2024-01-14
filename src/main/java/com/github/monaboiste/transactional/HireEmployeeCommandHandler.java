package com.github.monaboiste.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
class HireEmployeeCommandHandler implements CommandHandler<HireEmployeeCommand> {

    private final EmployeeRepository employeeRepository;
    private final DomainEventPublisher eventPublisher;
    private final PlatformTransactionManager transactionManager;

    @Override
    //@Transactional
    public void handle(HireEmployeeCommand command) {
        log.info("Requested new employee");

        Employee newEmployee = new Employee();
        newEmployee.domainId(command.employeeId());
        newEmployee.firstName(command.firstName());
        newEmployee.lastName(command.lastName());

        transactionWithoutResults(status -> {
            employeeRepository.save(newEmployee);
            eventPublisher.publish(new EmployeeHired(newEmployee.domainId().toString()));
        });
    }

    private void transactionWithoutResults(Consumer<TransactionStatus> action) {
        new TransactionTemplate(transactionManager).execute(status -> {
            action.accept(status);
            return null;
        });
    }
}

package com.github.monaboiste.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

@Slf4j
@Component
class EmployeeHiredEventListener implements DomainEventListener<EmployeeHired> {
    // e.g. send mail, notify external system to grant permission etc...
    // such scenarios should be handled with e.g. transactional outbox or event sourcing
    // https://dzone.com/articles/transaction-synchronization-and-spring-application

    private final EmployeeActivationService employeeActivationService;

    EmployeeHiredEventListener(EmployeeRepository employeeRepository,
                               PlatformTransactionManager transactionManager) {
        this.employeeActivationService = new EmployeeActivationService(employeeRepository, transactionManager);
    }

    @Override
    // Single transaction in Spring framework is by default thread-bounded
    //
    // @EventListener(condition = "'EmployeeHired'.equals(#event.name())")
    // If we use @EventListener, the listener is called synchronously within bounds of the same transaction.
    // But we want the employee to be already created, before the listener is invoked. We wouldn't like the transaction
    // to rollback if there's mail delivery error for instance.
    //
    // @TransactionalEventListener has ability to collaborate with surrounding transaction's phase - we register
    // the callbacks to be invoked after the transaction is being completed (transaction synchronization).
    // By default, the callback is invoked after commit (AFTER_COMMIT setting). If there's no transaction running, the
    // method won't be executed.
    @TransactionalEventListener(condition = "'EmployeeHired'.equals(#event.name())")
    public void process(EmployeeHired event) {
        log.info("Received event [{}]", event.eventId());
        employeeActivationService.activate(event.employee());
    }

    @RequiredArgsConstructor
    static class EmployeeActivationService {
        private final EmployeeRepository employeeRepository;
        private final PlatformTransactionManager transactionManager;

        void activate(Employee employee) {
            String token = generateToken(employee);

            var tx = new TransactionTemplate(transactionManager);
            tx.setPropagationBehavior(PROPAGATION_REQUIRES_NEW); // start new transaction, as previous was already committed
            tx.executeWithoutResult(status -> {
                employee.activateWith(token);
                employeeRepository.save(employee);
            });
        }

        private String generateToken(Employee employee) {
            return String.valueOf(employee.hashCode());
        }
    }
}

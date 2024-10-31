package com.github.monaboiste.transactional.domain.employee;

import com.github.monaboiste.transactional.domain.event.BatchDomainEvent;
import com.github.monaboiste.transactional.domain.event.DomainEvent;
import com.github.monaboiste.transactional.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class EmployeeActivationService implements ActivateEmployeeUseCase {

    private final EmployeeReadRepository employeeReadRepository;
    private final EmployeeWriteRepository employeeWriteRepository;
    private final DomainEventPublisher<EmployeeSnapshot> domainEventPublisher;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void activate(EmployeeId employeeId) {
        log.info("Activating {}", employeeId);

        var tx = new TransactionTemplate(transactionManager);
        tx.executeWithoutResult(status -> {
            var potentialEmployee = employeeReadRepository.findById(employeeId);
            if (potentialEmployee.isEmpty()) {
                throw new RuntimeException(); // todo: domain event
            }
            Employee newEmployee = potentialEmployee.get();
            if (newEmployee.active()) {
                return;
            }
            newEmployee.activate();
            employeeWriteRepository.save(newEmployee);

            List<DomainEvent<EmployeeSnapshot>> events = newEmployee.flushPendingEvents();
            domainEventPublisher.publish(new BatchDomainEvent<>(events));
        });
    }
}

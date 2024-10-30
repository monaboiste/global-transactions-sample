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
class EmploymentService implements HireEmployeeUseCase {

    private final EmployeeWriteRepository employeeWriteRepository;
    private final DomainEventPublisher<EmployeeSnapshot> domainEventPublisher;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void hire(Employee employee) {
        log.info("Hiring {}", employee.employeeId());

        var tx = new TransactionTemplate(transactionManager);
        tx.executeWithoutResult(status -> {
            employeeWriteRepository.save(employee);
            List<DomainEvent<EmployeeSnapshot>> events = employee.flushPendingEvents();
            domainEventPublisher.publish(new BatchDomainEvent<>(events));
        });
    }
}

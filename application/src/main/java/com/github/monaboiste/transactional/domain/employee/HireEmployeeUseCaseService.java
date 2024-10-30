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
class HireEmployeeUseCaseService implements HireEmployeeUseCase {

    private final PlatformTransactionManager transactionManager;
    private final EmployeeWriteRepository employeeWriteRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void hire(Employee employee) { // todo: use a command ?
        log.info("Hiring {}", employee.employeeId());

        var tx = new TransactionTemplate(transactionManager);
        tx.executeWithoutResult(status -> {
            employeeWriteRepository.save(employee);
            List<DomainEvent> events = employee.flushPendingEvents();
            domainEventPublisher.publish(new BatchDomainEvent(events));
        });
    }
}

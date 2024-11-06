package com.github.monaboiste.transactional.domain.employee;

import com.github.monaboiste.transactional.domain.event.BatchDomainEvent;
import com.github.monaboiste.transactional.domain.event.DomainEvent;
import com.github.monaboiste.transactional.domain.event.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
class PublishingEventEmployeeRepository implements EmployeeWriteRepository {

    private final DomainEventPublisher<EmployeeSnapshot> domainEventPublisher;
    private final EmployeeWriteRepository delegate;

    PublishingEventEmployeeRepository(DomainEventPublisher<EmployeeSnapshot> domainEventPublisher,
                                      @Qualifier("employeeH2JdbcDao")
                                      EmployeeWriteRepository delegate) {
        this.domainEventPublisher = domainEventPublisher;
        this.delegate = delegate;
    }

    @Override
    public void save(Employee employee) {
        List<DomainEvent<EmployeeSnapshot>> domainEvents = employee.flushPendingEvents();
        delegate.save(employee);
        domainEventPublisher.publish(new BatchDomainEvent<>(domainEvents));
    }
}

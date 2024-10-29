package com.github.monaboiste.transactional.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContextAwareEmployeeFactory implements EmployeeFactory {

    private final List<EventPublisher> eventPublishers;

    @Override
    public Employee create(UUID domainId,
                           String firstName,
                           String lastName,
                           boolean active) {
        return new Employee(domainId, firstName, lastName, active, this.eventPublishers);
    }

    @Override
    public Employee create(final String firstName, final String lastName) {
        return new Employee(firstName, lastName, this.eventPublishers);
    }
}

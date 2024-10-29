package com.github.monaboiste.transactional.domain.employee;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmployeeFactoryBean implements EmployeeFactory {

    @Override
    public Employee newEmployee(UUID domainId,
                                String firstName,
                                String lastName,
                                boolean active) {
        return new Employee(domainId, firstName, lastName, active);
    }

    @Override
    public Employee newEmployee(String firstName, String lastName) {
        return new Employee(firstName, lastName);
    }
}

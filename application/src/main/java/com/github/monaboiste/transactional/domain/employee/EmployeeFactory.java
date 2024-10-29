package com.github.monaboiste.transactional.domain.employee;

import java.util.UUID;

public interface EmployeeFactory {

    Employee newEmployee(final UUID domainId,
                         String firstName,
                         String lastName,
                         boolean active
    );

    Employee newEmployee(String firstName,
                         String lastName
    );
}

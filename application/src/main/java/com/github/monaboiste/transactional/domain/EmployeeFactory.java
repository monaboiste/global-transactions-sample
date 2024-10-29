package com.github.monaboiste.transactional.domain;

import java.util.UUID;

public interface EmployeeFactory {

    Employee create(final UUID domainId,
                    String firstName,
                    String lastName,
                    boolean active
    );

    Employee create(String firstName,
                    String lastName
    );
}

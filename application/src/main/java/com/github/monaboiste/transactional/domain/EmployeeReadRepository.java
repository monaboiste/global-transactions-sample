package com.github.monaboiste.transactional.domain;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeReadRepository {

    Optional<Employee> findById(UUID employeeId);
}

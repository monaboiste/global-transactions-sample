package com.github.monaboiste.transactional.domain.employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeReadRepository {

    Optional<Employee> findById(UUID employeeId);
}

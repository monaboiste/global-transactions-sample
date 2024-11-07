package com.github.monaboiste.transactional.employee;

import java.util.Optional;

public interface EmployeeReadRepository {

    Optional<Employee> findById(EmployeeId employeeId);
}

package com.github.monaboiste.transactional;

public interface EmployeeRepository extends EmployeeReadRepository {
    Employee save(Employee entity);
}

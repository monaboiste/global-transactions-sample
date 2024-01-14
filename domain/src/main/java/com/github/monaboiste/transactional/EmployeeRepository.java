package com.github.monaboiste.transactional;

import com.github.monaboiste.transactional.EmployeeReadRepository;

public interface EmployeeRepository extends EmployeeReadRepository {
    Employee save(Employee entity);
}

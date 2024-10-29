package com.github.monaboiste.transactional.domain.employee;

public interface EmployeeWriteRepository {

    Employee save(Employee employee);
}

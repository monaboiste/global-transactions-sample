package com.github.monaboiste.transactional.domain;

public interface EmployeeWriteRepository {

    Employee save(Employee employee);
}

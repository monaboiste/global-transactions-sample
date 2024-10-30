package com.github.monaboiste.transactional.domain.employee;

public interface EmployeeWriteRepository {

    void save(Employee employee);
}

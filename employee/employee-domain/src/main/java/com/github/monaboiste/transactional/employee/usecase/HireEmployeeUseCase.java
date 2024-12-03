package com.github.monaboiste.transactional.employee.usecase;

import com.github.monaboiste.transactional.employee.Employee;

/**
 * Hire a new employee.
 */
public interface HireEmployeeUseCase {

    void hire(Employee employee);
}


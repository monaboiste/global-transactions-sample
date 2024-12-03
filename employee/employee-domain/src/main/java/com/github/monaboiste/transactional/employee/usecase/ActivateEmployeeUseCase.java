package com.github.monaboiste.transactional.employee.usecase;

import com.github.monaboiste.transactional.employee.EmployeeId;

/**
 * Activate employee account.
 * This operation does not need a strict version
 * checking.
 */
public interface ActivateEmployeeUseCase {

    void activate(EmployeeId employeeId);
}

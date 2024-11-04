package com.github.monaboiste.transactional.domain.employee;

/**
 * Activate employee account.
 * This operation does not need a strict version
 * checking.
 */
public interface ActivateEmployeeUseCase {

    void activate(EmployeeId employeeId);
}

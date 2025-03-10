package com.github.monaboiste.transactional.employee.usecase;

import com.github.monaboiste.transactional.employee.Employee;
import com.github.monaboiste.transactional.employee.EmployeeId;
import com.github.monaboiste.transactional.employee.EmployeeReadRepository;
import com.github.monaboiste.transactional.employee.EmployeeWriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
class EmployeeActivationService implements ActivateEmployeeUseCase {

    private final EmployeeReadRepository employeeReadRepository;
    private final EmployeeWriteRepository employeeWriteRepository;
    private final PlatformTransactionManager transactionManager;

    public EmployeeActivationService(EmployeeReadRepository employeeReadRepository,
                                     EmployeeWriteRepository employeeWriteRepository,
                                     @Qualifier("employeeTransactionManager") PlatformTransactionManager transactionManager) {
        this.employeeReadRepository = employeeReadRepository;
        this.employeeWriteRepository = employeeWriteRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void activate(EmployeeId employeeId) {
        log.info("Activating {}", employeeId);

        var tx = new TransactionTemplate(transactionManager);
        tx.executeWithoutResult(status -> {
            var employee = employeeReadRepository.findById(employeeId)
                    .orElseThrow();// todo: domain event

            if (employee.active()) {
                return;
            }
            employee.activate();
            employeeWriteRepository.save(employee);
        });
    }
}

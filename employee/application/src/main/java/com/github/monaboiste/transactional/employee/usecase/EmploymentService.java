package com.github.monaboiste.transactional.employee.usecase;

import com.github.monaboiste.transactional.employee.Employee;
import com.github.monaboiste.transactional.employee.EmployeeWriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
class EmploymentService implements HireEmployeeUseCase {

    private final EmployeeWriteRepository employeeWriteRepository;
    private final PlatformTransactionManager transactionManager;

    public EmploymentService(EmployeeWriteRepository employeeWriteRepository,
                             @Qualifier("employeeTransactionManager") PlatformTransactionManager transactionManager) {
        this.employeeWriteRepository = employeeWriteRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void hire(Employee employee) {
        log.info("Hiring {}", employee.employeeId());

        var tx = new TransactionTemplate(transactionManager);
        tx.executeWithoutResult(status -> {
            employee.hire();
            employeeWriteRepository.save(employee);
        });
    }
}

package com.github.monaboiste.transactional.employee.event;

import com.github.monaboiste.transactional.employee.Employee;
import com.github.monaboiste.transactional.event.Snapshot;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmployeeSnapshot implements Snapshot {
    private final String employeeId;
    private final String firstName;
    private final String lastName;
    private final String workEmail;
    private final boolean active;

    private final String version;

    public EmployeeSnapshot(Employee employee) {
        this.employeeId = employee.employeeId().toString();
        this.firstName = employee.firstName();
        this.lastName = employee.lastName();
        this.workEmail = employee.workEmail().toString();
        this.active = employee.active();
        this.version = employee.version();
    }
}

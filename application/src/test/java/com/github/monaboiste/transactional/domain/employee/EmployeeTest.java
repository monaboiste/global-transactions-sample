package com.github.monaboiste.transactional.domain.employee;

import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void test_employee() {
        Employee employee = new Employee(new EmployeeId(), "Joe", "Doe", new Email("xx@xx.pl"));
        var events = employee.peek();
        System.out.println(events);
    }
}
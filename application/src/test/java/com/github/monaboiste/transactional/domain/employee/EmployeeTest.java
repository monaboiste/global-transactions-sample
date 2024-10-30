package com.github.monaboiste.transactional.domain.employee;

import com.github.monaboiste.transactional.domain.event.Event;
import org.junit.jupiter.api.Test;

import java.util.List;

class EmployeeTest {

    @Test
    void test_employee() {
        Employee employee = new Employee("Joe", "Doe");
        List<Event> events = employee.peek();
        System.out.println(events);
    }
}
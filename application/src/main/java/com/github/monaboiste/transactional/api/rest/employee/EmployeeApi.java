package com.github.monaboiste.transactional.api.rest.employee;

import com.github.monaboiste.transactional.domain.employee.Email;
import com.github.monaboiste.transactional.domain.employee.Employee;
import com.github.monaboiste.transactional.domain.employee.EmployeeId;
import com.github.monaboiste.transactional.domain.employee.HireEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
class EmployeeApi {

    private final HireEmployeeUseCase hireEmployeeUseCase;

    @PostMapping
    public ResponseEntity<Void> hire(@RequestBody EmployeeResource employeeResource) {
        EmployeeId employeeId = new EmployeeId();
        Employee employee = new Employee(employeeId,
                employeeResource.firstName(), employeeResource.lastName(), new Email(employeeResource.workEmail()));
        hireEmployeeUseCase.hire(employee);

        URI location = UriComponentsBuilder
                .fromUriString("/employees/{employeeId}")
                .build(employee.employeeId().toString());

        return ResponseEntity.created(location).build();
    }
}

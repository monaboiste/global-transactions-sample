package com.github.monaboiste.transactional.api.rest.employee;

import com.github.monaboiste.transactional.domain.employee.ActivateEmployeeUseCase;
import com.github.monaboiste.transactional.domain.employee.Email;
import com.github.monaboiste.transactional.domain.employee.Employee;
import com.github.monaboiste.transactional.domain.employee.EmployeeId;
import com.github.monaboiste.transactional.domain.employee.HireEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
class EmployeeApi {

    private final HireEmployeeUseCase hireEmployeeUseCase;
    private final ActivateEmployeeUseCase activateEmployeeUseCase;

    @PostMapping
    public ResponseEntity<Void> hire(@RequestBody EmployeeResource employeeResource) {
        log.info("Received hire command");
        EmployeeId employeeId = new EmployeeId();
        Employee employee = new Employee(employeeId,
                employeeResource.firstName(),
                employeeResource.lastName(),
                new Email(employeeResource.workEmail()));
        hireEmployeeUseCase.hire(employee);

        URI location = UriComponentsBuilder
                .fromUriString("/employees/{employeeId}")
                .build(employee.employeeId().toString());

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{employeeId}/activation")
    public ResponseEntity<Void> activate(@PathVariable UUID employeeId) {
        log.info("Received activate command");
        activateEmployeeUseCase.activate(new EmployeeId(employeeId));
        return ResponseEntity.noContent().build();
    }
}

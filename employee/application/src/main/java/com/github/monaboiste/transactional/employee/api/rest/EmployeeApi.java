package com.github.monaboiste.transactional.employee.api.rest;

import com.github.monaboiste.transactional.domain.Email;
import com.github.monaboiste.transactional.employee.Employee;
import com.github.monaboiste.transactional.employee.EmployeeId;
import com.github.monaboiste.transactional.employee.EmployeeReadRepository;
import com.github.monaboiste.transactional.employee.usecase.ActivateEmployeeUseCase;
import com.github.monaboiste.transactional.employee.usecase.HireEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * Employee resource.
 * <p>
 * Dev notes:
 * <ul>
 *     <li>we may separate read and write operations later when this class grows</li>
 *     <li>maintain eTag and cache control headers</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
class EmployeeApi {

    private final HireEmployeeUseCase hireEmployeeUseCase;
    private final ActivateEmployeeUseCase activateEmployeeUseCase;

    private final EmployeeReadRepository employeeReadRepository;

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
                .build(employee.employeeId().value());

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{employeeId}/activation")
    public ResponseEntity<Void> activate(@PathVariable EmployeeId employeeId) {
        log.info("Received activate command");
        activateEmployeeUseCase.activate(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResource> getEmployeeById(
            @PathVariable EmployeeId employeeId,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {

        Optional<Employee> potentialEmployee = employeeReadRepository.findById(employeeId);
        if (potentialEmployee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Employee employee = potentialEmployee.get();
        String eTag = employee.version();

        if (eTag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(eTag)
                    .build();
        }

        EmployeeResource body = new EmployeeResource(
                employee.employeeId().value(),
                employee.firstName(),
                employee.lastName(),
                employee.workEmail().value(),
                employee.active(),
                eTag);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .eTag(eTag)
                .body(body);
    }
}

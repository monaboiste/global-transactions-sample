package com.github.monaboiste.transactional.domain.employee;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
class EmployeeJdbcDao implements EmployeeReadRepository, EmployeeWriteRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final EmployeeFactory employeeFactory;

    EmployeeJdbcDao(final NamedParameterJdbcTemplate jdbc,
                    final EmployeeFactory employeeFactory) {
        this.jdbc = jdbc;
        this.employeeFactory = employeeFactory;
    }

    @Override
    public Optional<Employee> findById(final UUID employeeId) {
        Employee employee = jdbc.queryForObject("""
                        select domain_id, first_name, last_name, is_active from employees
                        where domain_id = :employeeId
                        """,
                Map.of("employeeId", employeeId.toString()),
                (rs, rowNum) ->
                        employeeFactory.newEmployee(
                                UUID.fromString(rs.getString("domain_id")),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getBoolean("is_active")
                        ));
        return Optional.ofNullable(employee);
    }

    @Override
    public Employee save(final Employee employee) {
        jdbc.update("""
                        insert into employees(domain_id, first_name, last_name, is_active) 
                        values (:domainId, :firstName, :lastName, :active)
                        """,
                Map.of(
                        "domainId", employee.domainId(),
                        "firstName", employee.firstName(),
                        "lastName", employee.lastName(),
                        "active", employee.active()
                ));
        return employee;
    }
}

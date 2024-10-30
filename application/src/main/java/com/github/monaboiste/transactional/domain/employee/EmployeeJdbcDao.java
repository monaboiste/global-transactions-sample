package com.github.monaboiste.transactional.domain.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
class EmployeeJdbcDao implements EmployeeReadRepository, EmployeeWriteRepository {

    private final NamedParameterJdbcTemplate jdbc;

    EmployeeJdbcDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Employee> findById(final UUID employeeId) {
        String statement = """
                select employee_id, first_name, last_name, work_email, is_active from employees
                where employee_id = :employeeId
                """;
        var params = Map.of("employeeId", employeeId.toString());
        Employee employee = jdbc.queryForObject(statement, params, resultSetToEmployee);
        return Optional.ofNullable(employee);
    }

    @Override
    public void save(final Employee employee) {
        log.info("Persisting employee {}", employee.employeeId());
        String statement = """
                insert into employees(employee_id, first_name, last_name, work_email, is_active) 
                values (:employeeId, :firstName, :lastName, :workEmail, :active)
                """;
        var params = employeeToParams(employee);
        jdbc.update(statement, params);
    }

    private static Map<String, ? extends Serializable> employeeToParams(final Employee employee) {
        return Map.of(
                "employeeId", employee.employeeId().toString(),
                "firstName", employee.firstName(),
                "lastName", employee.lastName(),
                "workEmail", employee.workEmail().toString(),
                "active", employee.active()
        );
    }

    private static final RowMapper<Employee> resultSetToEmployee = (rs, rowNum) ->
            new Employee(
                    new EmployeeId(UUID.fromString(rs.getString("employee_id"))),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    new Email(rs.getString("work_email"), false),
                    rs.getBoolean("is_active")
            );
}

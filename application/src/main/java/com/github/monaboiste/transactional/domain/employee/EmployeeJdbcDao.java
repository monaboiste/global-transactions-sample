package com.github.monaboiste.transactional.domain.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component
class EmployeeJdbcDao implements EmployeeReadRepository, EmployeeWriteRepository {

    private static final RowMapper<Employee> resultSetToEmployee = (rs, rowNum) ->
            new Employee(
                    new EmployeeId(UUID.fromString(rs.getString("employee_id"))),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    new Email(rs.getString("work_email"), false),
                    rs.getBoolean("is_active")
            );
    private final NamedParameterJdbcTemplate jdbc;

    EmployeeJdbcDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
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

    @Override
    public Optional<Employee> findById(final EmployeeId employeeId) {
        String statement = """
                select employee_id, first_name, last_name, work_email, is_active from employees
                where employee_id = :employeeId
                """;
        var params = Map.of("employeeId", employeeId.toString());
        try (Stream<Employee> stream = jdbc.queryForStream(statement, params, resultSetToEmployee)) {
            return stream.findFirst();
        }
    }

    @Override
    public void save(final Employee employee) {
        log.info("Persisting employee {}", employee.employeeId());
        String statement = """
                merge into employees(employee_id, first_name, last_name, work_email, is_active)
                key (employee_id)
                values (:employeeId, :firstName, :lastName, :workEmail, :active)
                """;
        var params = employeeToParams(employee);
        jdbc.update(statement, params);
    }
}

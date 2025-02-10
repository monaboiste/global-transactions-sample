package com.github.monaboiste.transactional.employee;

import com.github.monaboiste.transactional.ValidatedEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("squid:S1192")
class EmployeeH2JdbcDao implements EmployeeReadRepository, EmployeeWriteRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Optional<Employee> findById(final EmployeeId employeeId) {
        log.info("Fetching employee {}", employeeId);
        String statement = """
                select employee_id, first_name, last_name, work_email, is_active, tcn from employees
                where employee_id = :employee_id
                """;
        var params = Map.of("employee_id", employeeId.value());
        try (Stream<Employee> stream = jdbc.queryForStream(statement, params, resultSetToEmployee)) {
            return stream.findFirst();
        }
    }

    @Override
    public void save(final Employee employee) {
        upsertWithOptimisticLocking(employee);
    }

    private void upsertWithOptimisticLocking(final Employee employee) {
        log.info("Persisting employee {}", employee.employeeId());
        String statement = """
                merge into employees e using dual
                on e.employee_id = :employee_id
                   when not matched then
                    insert (employee_id,
                            first_name,
                            last_name,
                            work_email,
                            is_active,
                            tcn)
                       values (:employee_id,
                               :first_name,
                               :last_name,
                               :work_email,
                               :active,
                               :tcn)
                when matched and e.tcn < :tcn then
                    update
                    set e.first_name = :first_name,
                        e.last_name  = :last_name,
                        e.work_email = :work_email,
                        e.is_active  = :active,
                        e.tcn        = e.tcn
                """;
        var params = employeeToParams.apply(employee);
        int rowsAffected = jdbc.update(statement, params);
        if (rowsAffected == 0) {
            throw new RuntimeException("The record was modified by another transaction"); // todo: throw optimisic locking exception
        }
    }

    private final Function<Employee, Map<String, ? extends Serializable>> employeeToParams =
            employee -> Map.of(
                    "employee_id", employee.employeeId().value().toString(),
                    "first_name", employee.firstName(),
                    "last_name", employee.lastName(),
                    "work_email", employee.workEmail().value(),
                    "active", employee.active(),
                    "tcn", employee.version()
            );

    private final RowMapper<Employee> resultSetToEmployee = (rs, rowNum) ->
            new Employee(
                    new EmployeeId(UUID.fromString(rs.getString("employee_id"))),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    new ValidatedEmail(rs.getString("work_email")),
                    rs.getBoolean("is_active"),
                    rs.getInt("tcn")
            );
}

package com.github.monaboiste.transactional.domain;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
class CommunicationJdbcDao implements CommunicationReadRepository, CommunicationWriteRepository {

    private final NamedParameterJdbcTemplate jdbc;

    CommunicationJdbcDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Communication> findByEmployeeId(final UUID employeeId) {
        return jdbc.query("""
                        select employee_id, type from communication where employee_id = :employeeId
                        """,
                Map.of("employeeId", employeeId.toString()),
                (rs, rowNum) -> new Communication(
                        UUID.fromString(rs.getString("employee_id")),
                        Communication.Type.valueOf(rs.getString("type"))));
    }

    @Override
    public Communication save(final Communication communication) {
        jdbc.update("""
                        insert into communications(employee_id, type)
                        values (:employeeId, :type)
                        """,
                Map.of("employeeId", communication.employeeId(), "type", communication.type()));
        return communication;
    }
}

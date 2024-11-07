package com.github.monaboiste.transactional.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration(proxyBeanMethods = false)
class EmployeeConfig {

    @Bean
    EmployeeReadRepository employeeReadRepository(NamedParameterJdbcTemplate jdbc) {
        return new EmployeeH2JdbcDao(jdbc);
    }

    @Bean
    EmployeeWriteRepository employeeWriteRepository(NamedParameterJdbcTemplate jdbc) {
        return new EmployeeH2JdbcDao(jdbc);
    }
}

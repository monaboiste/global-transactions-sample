package com.github.monaboiste.transactional.employee;

import com.github.monaboiste.transactional.employee.event.EmployeeSnapshot;
import com.github.monaboiste.transactional.event.ApplicationDomainEventPublisher;
import com.github.monaboiste.transactional.event.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration(proxyBeanMethods = false)
class EmployeeConfig {

    @Bean
    EmployeeH2JdbcDao employeeRepository(NamedParameterJdbcTemplate jdbc) {
        return new EmployeeH2JdbcDao(jdbc);
    }

    @Bean
    DomainEventPublisher<EmployeeSnapshot> domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new ApplicationDomainEventPublisher<>(applicationEventPublisher);
    }
}

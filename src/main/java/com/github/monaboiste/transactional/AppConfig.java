package com.github.monaboiste.transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AppConfig {

    private final EmployeeRepositoryJpa employeeRepositoryJpa;

    @Bean
    public EmployeeRepository employeeRepository() {
        return employeeRepositoryJpa;
    }

    @Bean
    public EmployeeReadRepository employeeReadRepository(EmployeeRepository employeeRepository) {
        return employeeRepository;
    }

    @Bean
    DomainEventPublisher domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringEventBus(applicationEventPublisher);
    }
}

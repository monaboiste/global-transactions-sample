package com.github.monaboiste.transactional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

    @Bean
    public EmployeeRepository employeeRepository() {
        return new EmployeeRepositoryInMemory();
    }

    @Bean
    public EmployeeReadRepository employeeReadRepository(EmployeeRepository employeeRepository) {
        return employeeRepository;
    }
}

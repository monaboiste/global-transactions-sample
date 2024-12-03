package com.github.monaboiste.transactional.employee;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
class EmployeeDataSourceConfig {

    @Bean
    public NamedParameterJdbcTemplate employeeJdbcTemplate(@Qualifier("employeeDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager employeeTransactionManager(@Qualifier("employeeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.employee-datasource.hikari")
    public DataSource employeeDataSource(@Qualifier("employeeDataSourceProperties")
                                         DataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.employee-datasource")
    public DataSourceProperties employeeDataSourceProperties() {
        return new DataSourceProperties();
    }
}

package com.github.monaboiste.transactional.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.monaboiste.transactional.domain.Employee;
import com.github.monaboiste.transactional.domain.EmployeeFactory;
import com.github.monaboiste.transactional.domain.EmployeeReadRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.UUID;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.POST;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.noContent;
import static org.springframework.web.servlet.function.ServerResponse.notFound;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Configuration
class EmployeeEndpoint {

    private final EmployeeFactory employeeFactory;
    private final EmployeeReadRepository employeeReadRepository;

    EmployeeEndpoint(final EmployeeFactory employeeFactory,
                     final EmployeeReadRepository employeeReadRepository) {
        this.employeeFactory = employeeFactory;
        this.employeeReadRepository = employeeReadRepository;
    }

    @Bean
    @JsonView(EmployeeResource.Read.class)
    RouterFunction<ServerResponse> getEmployeeById() {
        return route(GET("/employees/{employeeId}"), request -> {
            UUID employeeId = UUID.fromString(request.pathVariable("employeeId"));

            return employeeReadRepository.findById(employeeId)
                    .map(employee -> {
                        var responseBody = new EmployeeResource(
                                employee.domainId(), employee.firstName(), employee.lastName());
                        return ok().body(responseBody);
                    })
                    .orElse(notFound().build());
        });
    }

    @Bean
    RouterFunction<ServerResponse> hireEmployee() {
        return route(POST("/employees"), request -> {
            var requestBody = request.body(EmployeeResource.class);
            Employee employee = employeeFactory.create(requestBody.firstName(), requestBody.lastName());


            return noContent().build();
        });
    }

    @Bean
    RouterFunction<ServerResponse> confirmEmployee() {
        return route(POST("/employees/{employeeId}"), request -> {
            UUID employeeId = UUID.fromString(request.pathVariable("employeeId"));
            return noContent().build();
        });
    }
}

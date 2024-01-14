package com.github.monaboiste.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.POST;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.created;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Slf4j
@Configuration
@RequiredArgsConstructor
class EmployeeEndpoint {
    private final EmployeeReadRepository employeeReadRepository;
    private final CommandDispatcher commandDispatcher;

    @Bean
    RouterFunction<ServerResponse> getEmployeeById() {
        return route(GET("/employees/{employeeId}"), request -> {
            UUID employeeId = UUID.fromString(request.pathVariable("employeeId"));
            Employee employee = employeeReadRepository.getById(employeeId);
            if (employee == null) {
                throw new RuntimeException("Employee [" + employeeId + "] not found");
            }
            return ok().body(employee);
        });
    }

    @Bean
    RouterFunction<ServerResponse> hireEmployee() {
        return route(POST("/employees"), request -> {
            HireEmployeeCommand command = request.body(HireEmployeeCommand.class);
            command.employeeId(UUID.randomUUID());

            commandDispatcher.resolve(HireEmployeeCommand.class).handle(command);

            return created(UriComponentsBuilder.newInstance()
                    .path("/employees/{employeeId}")
                    .buildAndExpand(command.employeeId())
                    .toUri())
                    .build();
        });
    }
}

package com.github.monaboiste.transactional.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.monaboiste.transactional.Employee;
import com.github.monaboiste.transactional.EmployeeReadRepository;
import com.github.monaboiste.transactional.command.CommandDispatcher;
import com.github.monaboiste.transactional.command.HireEmployeeCommand;
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
    @JsonView(EmployeeView.Read.class)
    RouterFunction<ServerResponse> getEmployeeById() {
        return route(GET("/employees/{employeeId}"), request -> {
            UUID employeeId = UUID.fromString(request.pathVariable("employeeId"));

            Employee employee = employeeReadRepository.getById(employeeId);
            if (employee == null) {
                throw new RuntimeException("Employee [" + employeeId + "] not found");
            }

            var responseBody = new EmployeeView(employee.domainId(), employee.firstName(), employee.lastName());
            return ok().body(responseBody);
        });
    }

    @Bean
    RouterFunction<ServerResponse> hireEmployee() {
        return route(POST("/employees"), request -> {
            var requestBody = request.body(EmployeeView.class);
            var command = new HireEmployeeCommand(requestBody.firstName(), requestBody.firstName());

            commandDispatcher.resolve(HireEmployeeCommand.class).handle(command);

            return created(UriComponentsBuilder.newInstance()
                    .path("/employees/{employeeId}")
                    .buildAndExpand(command.employeeId())
                    .toUri())
                    .build();
        });
    }
}

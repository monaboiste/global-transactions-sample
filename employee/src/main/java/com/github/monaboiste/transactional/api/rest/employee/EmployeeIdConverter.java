package com.github.monaboiste.transactional.api.rest.employee;

import com.github.monaboiste.transactional.domain.employee.EmployeeId;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class EmployeeIdConverter implements Converter<String, EmployeeId> {

    @Override
    public EmployeeId convert(@NotNull String source) {
        return new EmployeeId(UUID.fromString(source));
    }
}

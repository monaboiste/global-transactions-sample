package com.github.monaboiste.transactional.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Communication {

    private final UUID employeeId;
    private final Type type;

    public Communication(final UUID employeeId, final Type type) {
        this.employeeId = employeeId;
        this.type = type;
    }

    public enum Type {
        EMAIL
    }
}

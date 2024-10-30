package com.github.monaboiste.transactional.domain.employee;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Email {

    private final String value;

    public Email(String value) {
        this(value, true);
    }

    Email(String value, boolean validate) {
        if (value == null) {
            throw new IllegalArgumentException(); // todo: domain exception
        }
        if (validate && !new EmailValidator().validate(value)) {
            throw new IllegalArgumentException(); // todo: domain exception
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value();
    }
}

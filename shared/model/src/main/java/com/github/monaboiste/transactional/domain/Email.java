package com.github.monaboiste.transactional.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Email {

    private final String value;

    public Email(String value) {
        this(value, false);
    }

    /**
     * Package-private constructor for rehydration without validation.
     *
     * @param value     email text.
     * @param validated set to {@code true}, if the object has been already
     *                  validated.
     */
    Email(String value, boolean validated) {
        if (value == null) {
            throw new IllegalArgumentException(); // todo: domain exception
        }
        if (!(validated || new EmailValidator().validate(value))) {
            throw new IllegalArgumentException(); // todo: domain exception
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

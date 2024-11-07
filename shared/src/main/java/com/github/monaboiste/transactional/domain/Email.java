package com.github.monaboiste.transactional.domain;

import java.util.Objects;

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

    public String value() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public String toString() {
        return value;
    }
}

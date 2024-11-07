package com.github.monaboiste.transactional.domain;

/**
 * Rehydrated {@link Email} object.
 * TODO: move to persistence module
 */
public class ValidatedEmail extends Email {

    public ValidatedEmail(String value) {
        super(value, false);
    }
}

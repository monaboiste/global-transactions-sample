package com.github.monaboiste.transactional;

import java.util.regex.Pattern;

public class EmailValidator {

    /**
     * RFC 5322 compliant regex pattern
     */
    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public boolean validate(String email) {
        return pattern.matcher(email).matches();
    }
}

package com.soni.usermanagement.exception.error;

public class PasswordNotValid extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PasswordNotValid(String password) {
        super("Password is not valid: " + password);
    }
}
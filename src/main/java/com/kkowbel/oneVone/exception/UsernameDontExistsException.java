package com.kkowbel.oneVone.exception;

public class UsernameDontExistsException extends RuntimeException {
    public UsernameDontExistsException(String message) {
        super(message);
    }
}

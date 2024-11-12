package com.kkowbel.oneVone.exception;

public class GameDoesNotExistsException extends RuntimeException {
    public GameDoesNotExistsException(String message) {
        super(message);
    }
}

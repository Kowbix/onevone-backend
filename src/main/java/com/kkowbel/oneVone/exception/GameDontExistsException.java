package com.kkowbel.oneVone.exception;

public class GameDontExistsException extends RuntimeException {
    public GameDontExistsException(String message) {
        super(message);
    }
}

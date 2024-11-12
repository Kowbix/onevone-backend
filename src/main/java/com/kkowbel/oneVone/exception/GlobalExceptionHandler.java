package com.kkowbel.oneVone.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> usernameAlreadyExists(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(UsernameDoesNotExistException.class)
    public ResponseEntity<String> usernameDontExists(UsernameDoesNotExistException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(SessionExceptions.class)
    public ResponseEntity<String> sessionExceptions(SessionExceptions e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(GameDoesNotExistsException.class)
    public ResponseEntity<String> gameDontExistsException(GameDoesNotExistsException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(FullGameException.class)
    public ResponseEntity<String> fullGameException(FullGameException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }
}

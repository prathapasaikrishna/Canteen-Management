package com.canteen.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace();
        String message = ex.getClass().getSimpleName();
        if (ex.getMessage() != null) {
            message += ": " + ex.getMessage();
        }
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            message += " | Cause: " + ex.getCause().getMessage();
        }
        return new ResponseEntity<>("Server Error: " + message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
package me.thesis.master.common.controllers;

import me.thesis.master.common.exceptions.KeyNotFoundException;
import me.thesis.master.common.exceptions.TooManyKeysException;
import me.thesis.master.common.exceptions.UserAlreadyExistsException;
import me.thesis.master.common.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TooManyKeysException.class, UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExists(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class, KeyNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<Object> handleNotFound(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Missing required request header: " + exception.getHeaderName());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Missing required request parameter: " + exception.getParameterName());
    }
}
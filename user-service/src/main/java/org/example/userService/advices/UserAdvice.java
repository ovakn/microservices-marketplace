package org.example.userService.advices;

import org.example.userService.DTOs.ExceptionResponse;
import org.example.userService.exceptions.NotCorrectPasswordException;
import org.example.userService.exceptions.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserAdvice {
    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotExistsException(UserNotExistsException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotCorrectPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleNotCorrectPasswordException(NotCorrectPasswordException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
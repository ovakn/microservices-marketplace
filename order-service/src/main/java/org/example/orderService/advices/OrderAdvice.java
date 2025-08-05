package org.example.orderService.advices;

import org.example.orderService.DTOs.ExceptionResponse;
import org.example.orderService.exceptions.OrderNotExistsException;
import org.example.orderService.exceptions.ProductNotAvailableException;
import org.example.orderService.exceptions.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderAdvice {
    @ExceptionHandler(OrderNotExistsException.class)
    public ResponseEntity<ExceptionResponse> handleOrderNotExistsException(OrderNotExistsException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotExistsException(UserNotExistsException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotAvailableException(ProductNotAvailableException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
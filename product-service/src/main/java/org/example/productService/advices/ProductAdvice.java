package org.example.productService.advices;

import org.example.productService.DTOs.ExceptionResponse;
import org.example.productService.exceptions.ProductNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductAdvice {
    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotExistsException(ProductNotExistsException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
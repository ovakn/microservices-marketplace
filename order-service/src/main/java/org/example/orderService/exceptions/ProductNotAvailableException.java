package org.example.orderService.exceptions;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException() {
        super("Some of products in your order is not available");
    }
}
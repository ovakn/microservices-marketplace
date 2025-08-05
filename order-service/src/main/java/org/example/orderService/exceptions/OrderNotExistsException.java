package org.example.orderService.exceptions;

public class OrderNotExistsException extends RuntimeException {
    public OrderNotExistsException(Long id) {
        super("Order with id(" + id + ") does not exist");
    }
}
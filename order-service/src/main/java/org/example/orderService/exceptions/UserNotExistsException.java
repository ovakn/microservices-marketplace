package org.example.orderService.exceptions;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException(Long id) {
        super("User with such id (" + id + ") does not exist");
    }
}
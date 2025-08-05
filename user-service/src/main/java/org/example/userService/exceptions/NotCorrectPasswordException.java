package org.example.userService.exceptions;

public class NotCorrectPasswordException extends RuntimeException {
    public NotCorrectPasswordException() {
        super("Wrong password was inputted");
    }
}
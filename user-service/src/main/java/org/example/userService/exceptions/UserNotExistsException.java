package org.example.userService.exceptions;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException(Long id) {
        super("User with such id (" + id + ") does not exist");
    }

    public UserNotExistsException(String userInformation) {
        super("User with such email or phone (" + userInformation + ") does not exist");
    }
}
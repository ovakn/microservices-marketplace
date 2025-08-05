package org.example.productService.exceptions;

public class ProductNotExistsException extends RuntimeException {
    public ProductNotExistsException(Long id) {
        super("User with such id (" + id + ") does not exist");
    }

    public ProductNotExistsException(String name) {
        super("User with such name (" + name + ") does not exist");
    }
}
package com.example.demo.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is specific to HTTP Status 404 - Not found. Any custom messages
 * can be added here.
 */
@ResponseStatus(NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String id) {
        super(String.format("Product id %s not found", id));
    }

    public static ProductNotFoundException notFound(String id) {
        return new ProductNotFoundException(id);
    }
}

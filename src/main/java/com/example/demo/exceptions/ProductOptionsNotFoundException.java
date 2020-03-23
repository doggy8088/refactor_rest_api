package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is specific to HTTP Status 404 - Not found. Any custom messages can be added here.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductOptionsNotFoundException extends RuntimeException {
    public ProductOptionsNotFoundException(String message) {
        super(message);
    }
}

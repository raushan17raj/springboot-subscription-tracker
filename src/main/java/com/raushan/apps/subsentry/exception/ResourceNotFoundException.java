package com.raushan.apps.subsentry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for when a resource is not found in the database.
 * Responds with HTTP 404 Not Found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        // Example: "User not found with id : 1"
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
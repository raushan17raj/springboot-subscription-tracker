package com.raushan.apps.subsentry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for handling all API-related errors.
 * Using @ResponseStatus ensures Spring Boot returns the correct HTTP status code.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
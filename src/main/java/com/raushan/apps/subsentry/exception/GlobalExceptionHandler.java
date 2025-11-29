package com.raushan.apps.subsentry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

/**
 * Catches exceptions from all @RestController classes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method handles the MethodArgumentNotValidException thrown by @Valid.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Sets the HTTP status to 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        // This map will hold our field-specific errors
        Map<String, String> errors = new HashMap<>();
        
        // Get all validation errors
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Create the final response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Validation Failed");
        responseBody.put("details", errors); // Here are your DTO messages!

        return responseBody;
    }

    /**
     * This method handles custom ApiException.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    public Map<String, Object> handleApiException(ApiException ex) {
        
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", ex.getMessage()); // e.g: The simple message "Email already registered!"
        return responseBody;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        responseBody.put("error", resourceNotFoundException.getMessage());
        return responseBody;
    }
}
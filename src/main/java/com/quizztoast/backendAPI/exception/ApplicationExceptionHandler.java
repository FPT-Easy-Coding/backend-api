package com.quizztoast.backendAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@ControllerAdvice
@RestControllerAdvice
public class ApplicationExceptionHandler {
    /**
     * Handle validation exceptions in the Java function.
     *
     * @param  ex	description of parameter
     * @return     description of return value
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation Failed");
        response.put("error", true);

        ErrorDetailsFormatter errorDetailsFormatter = new ErrorDetailsFormatter();
        List<Map<String, String>> errorDetailsList = errorDetailsFormatter.formatErrorDetails(ex);

        response.put("data", errorDetailsList);
        return response;
    }







    /**
     * Handle UsernameNotFoundException and return a map with error message.
     *
     * @param  ex  the UsernameNotFoundException to handle
     * @return     a map with the error message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User not found");
        response.put("error", true);

        List<Map<String, String>> errorDetailsList = formatErrorDetailsForUserNotFound(ex.getUserId());
        response.put("data", errorDetailsList);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailOrUsernameAlreadyTakenException.class)
    public Map<String, Object> handleEmailOrUsernameAlreadyTakenException(EmailOrUsernameAlreadyTakenException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation Failed");
        response.put("error", true);

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("fieldName", ex.getFieldName());
        errorDetails.put("errorMessage", ex.getErrorMessage());

        response.put("data", List.of(errorDetails));
        return response;
    }

    private List<Map<String, String>> formatErrorDetailsForUserNotFound(Long userId) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("fieldName", "userId");
        errorDetails.put("errorMessage", "User with ID " + userId + " not found");

        return List.of(errorDetails);
    }
    /**
     * Handles the EmailAlreadyTakenException and returns an error response map.
     *
     * @param  ex  the EmailAlreadyTakenException to be handled
     * @return     an error response map with the key "email" and the exception message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmailAlreadyTakenException.class)
    public Map<String, String> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
        return createErrorResponse("email", ex.getMessage());
    }


    /**
     * Creates an error response map with the given key and message.
     *
     * @param  key     the key for the error response
     * @param  message the message for the error response
     * @return         the error response map
     */
    private Map<String, String> createErrorResponse(String key, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(key, message);
        return errorResponse;
    }
    private Map<String, Object> handleUserNotFound(Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User not found");
        response.put("error", true);

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("fieldName", "userId");
        errorDetails.put("errorMessage", "User with ID " + userId + " not found");

        response.put("data", List.of(errorDetails));
        return response;
    }
}

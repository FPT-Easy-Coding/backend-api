package com.quizztoast.backendAPI.exception;

import org.springframework.http.HttpStatus;
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
        List<Map<String, Object>> errorDetailsList = errorDetailsFormatter.formatErrorDetails(ex);

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
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String,String> handleBusinessException (UsernameNotFoundException ex){
        Map< String , String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
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
}

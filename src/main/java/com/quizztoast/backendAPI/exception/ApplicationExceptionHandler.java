package com.quizztoast.backendAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
@RestControllerAdvice
public class ApplicationExceptionHandler {
    /**
     * Handles validation exceptions and returns a map of field names and error messages.
     *
     * @param  ex  the MethodArgumentNotValidException to be handled
     * @return     a map containing field names as keys and error messages as values
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationExceptions (MethodArgumentNotValidException ex){
        Map< String , String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String,String> handleBusinessException (UsernameNotFoundException ex){
        Map< String , String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }
}

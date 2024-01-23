package com.quizztoast.backendAPI.exception;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorDetailsFormatter {
    public List<Map<String, String>> formatErrorDetails(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errorDetailsList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> errorDetails = new HashMap<>();
            if (error instanceof FieldError) {
                errorDetails.put("fieldName", ((FieldError) error).getField());
            } else if (error instanceof ObjectError) {
                errorDetails.put("objectName", ((ObjectError) error).getObjectName());
            }
            errorDetails.put("errorMessage", error.getDefaultMessage());
            errorDetailsList.add(errorDetails);
        });
        return errorDetailsList;
    }
}

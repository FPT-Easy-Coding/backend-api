package com.quizztoast.backendAPI.exception;

import org.springframework.validation.FieldError;
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
            errorDetails.put("fieldName", ((FieldError) error).getField());
            errorDetails.put("errorMessage", error.getDefaultMessage());
            errorDetailsList.add(errorDetails);
        });
        return errorDetailsList;
    }

}

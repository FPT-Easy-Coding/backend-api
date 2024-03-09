package com.quiztoast.backend_api.exception;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorDetailsFormatter {
    public List<Map<String, Object>> formatErrorDetails(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> errorDetailsList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, Object> errorDetails = new HashMap<>();
            if (error instanceof FieldError) {
                errorDetails.put("fieldName", ((FieldError) error).getField());
            } else if (error instanceof ObjectError) {
                // Check for custom validation annotation @ValidCredentials
                if ("ValidCredentials".equals(error.getCode())) {
                    extractValidCredentialsDetails((ObjectError) error, errorDetails);
                } else {
                    errorDetails.put("fieldName", ((ObjectError) error).getObjectName());
                }
            }
            errorDetails.put("errorMessage", error.getDefaultMessage());
            errorDetailsList.add(errorDetails);
        });
        return errorDetailsList;
    }

    private void extractValidCredentialsDetails(ObjectError error, Map<String, Object> errorDetails) {
        Object[] arguments = error.getArguments();
        if (arguments.length > 1 && arguments[1] instanceof String[]) {
            String[] fields = (String[]) arguments[1];
            errorDetails.put("fieldName", fields);
        } else {
            errorDetails.put("fieldName", error.getObjectName());
        }
    }
}

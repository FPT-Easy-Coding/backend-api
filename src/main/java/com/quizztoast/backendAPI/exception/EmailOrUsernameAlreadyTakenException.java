package com.quizztoast.backendAPI.exception;

public class EmailOrUsernameAlreadyTakenException extends RuntimeException {
    private final String fieldName;
    private final String errorMessage;

    public EmailOrUsernameAlreadyTakenException(String fieldName, String errorMessage) {
        super(errorMessage);
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
package com.quiztoast.backend_api.exception;

import lombok.Getter;

@Getter
public class FormatException extends RuntimeException {
    private final String fieldName;
    private final String errorMessage;

    public FormatException(String fieldName, String errorMessage) {
        super(errorMessage);
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

}
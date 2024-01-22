package com.quizztoast.backendAPI.controller;

public class SimpleErrorResponse {

    private int statusCode;
    private String message;

    public SimpleErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
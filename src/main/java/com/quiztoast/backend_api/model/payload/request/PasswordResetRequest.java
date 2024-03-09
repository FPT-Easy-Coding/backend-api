package com.quiztoast.backend_api.model.payload.request;

import lombok.Data;

@Data
public class PasswordResetRequest {

    private String email;
    private String newPassword;
    private String confirmPassword;
}

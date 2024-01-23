package com.quizztoast.backendAPI.security.auth.auth_payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationRequest {
    private String email;
    private String code;
}

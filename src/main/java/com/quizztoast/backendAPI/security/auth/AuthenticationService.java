package com.quizztoast.backendAPI.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    String applicationUrl(HttpServletRequest request);
    AuthenticationResponse register(RegistrationRequest registrationRequest, HttpServletRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    AuthenticationResponse verifyCode(VerificationRequest verificationRequest);
}

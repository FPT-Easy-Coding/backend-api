package com.quizztoast.backendAPI.security.auth.auth_service;

import com.quizztoast.backendAPI.security.auth.auth_payload.AuthenticationRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegistrationRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.VerificationRequest;

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

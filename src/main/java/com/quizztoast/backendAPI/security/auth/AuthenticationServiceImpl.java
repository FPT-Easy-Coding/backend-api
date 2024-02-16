package com.quizztoast.backendAPI.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.quizztoast.backendAPI.event.publisher.PasswordResetEventPublisher;
import com.quizztoast.backendAPI.model.entity.token.Token;
import com.quizztoast.backendAPI.model.entity.token.TokenType;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.TokenRepository;
import com.quizztoast.backendAPI.repository.UserRepository;

import com.quizztoast.backendAPI.event.publisher.RegistrationEventPublisher;
import com.quizztoast.backendAPI.security.jwt.JWTService;
import com.quizztoast.backendAPI.security.tfa.TwoFactorAuthenticationService;

import com.quizztoast.backendAPI.service.user.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserServiceImpl userServiceImpl;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TwoFactorAuthenticationService tfaService;
    private final RegistrationEventPublisher eventPublisher;
    private final PasswordResetEventPublisher passwordResetEventPublisher;

    /**
     * Generates the application URL based on the provided HttpServletRequest.
     *
     * @param request the HttpServletRequest object
     * @return the generated application URL
     */
    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

    }

    /**
     * Generates a password reset URL for the given application URL and password reset token.
     *
     * @param applicationUrl     the application URL
     * @param passwordResetToken the password reset token
     * @return the generated password reset URL
     */
    public String generatePasswordResetUrl(String applicationUrl, String passwordResetToken) {
        return applicationUrl + "/api/v1/auth/reset-password?token=" + passwordResetToken;
    }

    /**
     * Sends a password reset email to the specified user.
     *
     * @param user             the user for whom the password is being reset
     * @param passwordResetUrl the URL for resetting the password
     */
    public void sendPasswordResetEmail(User user, String passwordResetUrl) {
        passwordResetEventPublisher.publishPasswordResetEvent(user, passwordResetUrl);
    }


    /**
     * Register a new user and generate authentication tokens.
     *
     * @param registrationRequest the request containing user registration information
     * @param request             the HTTP servlet request
     * @return the authentication response containing access and refresh tokens
     */
    public AuthenticationResponse register(RegistrationRequest registrationRequest, HttpServletRequest request) {
        var user = userServiceImpl.createUser(registrationRequest);
        eventPublisher.publishRegistrationEvent(user, applicationUrl(request));
        // If MFA enabled --> generate Secret
//            if (registerRequest.isMfaEnabled()) {
//                user.setSecret(tfaService.generateNewSecret());
//            }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
//                    .secretImageUri(tfaService.generateQrCodeImageUrl(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
//                    .mfaEnabled(user.isMfaEnabled())
                .build();

    }

    /**
     * Saves the user token.
     *
     * @param user     the user object
     * @param jwtToken the JWT token
     */
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Authenticates the user based on the provided authentication request.
     *
     * @param authenticationRequest the authentication request containing email and password
     * @return the authentication response with access token, refresh token, and user details
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();

//        if (!user.isVerified()) {
//            throw new RuntimeException("User is not verified");
//        }
        if (user.isMfaEnabled()) {
            return AuthenticationResponse.builder()
                    .accessToken("")
                    .refreshToken("")
                    .mfaEnabled(true)
                    .build();
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getUserId())
                .role(user.getRole())
                .mfaEnabled(false)
                .isBanned(user.isBanned())
                .build();

    }

    /**
     * Revokes all tokens for a given user.
     *
     * @param user the user whose tokens will be revoked
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Refreshes the token for the given request and response.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     */
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .mfaEnabled(false)
                        .userId(user.getUserId())
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    /**
     * Verify the code for user authentication.
     *
     * @param verificationRequest the verification request containing email and code
     * @return the authentication response containing access token and mfa status
     */
    public AuthenticationResponse verifyCode(
            VerificationRequest verificationRequest
    ) {
        User user = userRepository.findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user found with %S", verificationRequest))
                );
        if (tfaService.isOtpInvalid(user.getSecret(), verificationRequest.getCode())) {
            throw new BadCredentialsException("Code is not correct");
        }
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

}

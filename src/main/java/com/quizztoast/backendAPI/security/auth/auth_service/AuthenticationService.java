package com.quizztoast.backendAPI.security.auth.auth_service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.quizztoast.backendAPI.model.entity.token.Token;
import com.quizztoast.backendAPI.model.entity.token.TokenType;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.TokenRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth.auth_payload.AuthenticationRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.VerificationRequest;
import com.quizztoast.backendAPI.security.jwt.JWTService;
import com.quizztoast.backendAPI.security.tfa.TwoFactorAuthenticationService;

import com.quizztoast.backendAPI.service.impl.UserServiceImpl;
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
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserServiceImpl userServiceImpl;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TwoFactorAuthenticationService tfaService;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = userServiceImpl.createUser(registerRequest);
            // If MFA enabled --> generate Secret
            if (registerRequest.isMfaEnabled()) {
                user.setSecret(tfaService.generateNewSecret());
            }
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .secretImageUri(tfaService.generateQrCodeImageUrl(user.getSecret()))
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .mfaEnabled(user.isMfaEnabled())
                    .build();

    }

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

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );

            var user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow();

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
                    .build();

    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUserId());
        if(validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if(jwtService.isTokenValid(refreshToken,user)){
              var accessToken = jwtService.generateToken(user);
              revokeAllUserTokens(user);
              saveUserToken(user,accessToken);
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

    public AuthenticationResponse verifyCode(
            VerificationRequest verificationRequest
    ) {
        User user = userRepository.findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user found with %S",verificationRequest))
                );
        if (tfaService.isOtpInvalid(user.getSecret(),verificationRequest.getCode())){
            throw new BadCredentialsException("Code is not correct");
        }
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .mfaEnabled(user.isMfaEnabled())
                .build();
     }
}

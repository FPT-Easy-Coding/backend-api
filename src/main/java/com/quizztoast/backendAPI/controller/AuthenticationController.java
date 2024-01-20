package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.security.auth_payload.AuthenticationRequest;
import com.quizztoast.backendAPI.security.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth_payload.VerificationRequest;
import com.quizztoast.backendAPI.security.auth_service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Registers a new user",
            description = "Registers a new user with the provided details.",
            responses = {
                    @ApiResponse(
                            description = "Success. User registered successfully.",
                            responseCode = "200"
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = AuthenticationResponse.class)
//                            )
                    ),
                    @ApiResponse(
                            description = "Conflict. User with the provided username or email already exists.",
                            responseCode = "422"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @Valid
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * Authenticates a user.
     *
     * @param request The authentication request containing user credentials.
     * @return A ResponseEntity with an authentication response and a status code indicating the result of the authentication.
     */
    @Operation(
            summary = "Authenticates a user",
            description = "Authenticates a user with the provided credentials.",
            responses = {
                    @ApiResponse(
                            description = "Success. User authenticated successfully.",
                            responseCode = "200"
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = AuthenticationResponse.class)
//                            )
                    )
//                    ,
//                    @ApiResponse(
//                            description = "Unauthorized. Incorrect username or password.",
//                            responseCode = "401"
//                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @Valid
            @RequestBody AuthenticationRequest request
    ){
//        var response = authenticationService.register(request);
//        if(request.isMfaEnabled()){
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.accepted().build();
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @Operation(
            summary = "Refreshes the authentication token",
            description = "Refreshes the authentication token using the provided refresh token.",
            responses = {
                    @ApiResponse(
                            description = "Success. Token refreshed successfully.",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid refresh token provided.",
                            responseCode = "400"
                    )
//                    ,
//                    @ApiResponse(
//                            description = "Unauthorized. Invalid or expired refresh token.",
//                            responseCode = "401"
//                    ),

            }
    )
    @PostMapping("/refresh-token")
    public void refreshToken (
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(
            @RequestBody VerificationRequest verificationRequest
    ){
            return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
    }



}

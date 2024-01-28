package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.exception.EmailAlreadyTakenException;
import com.quizztoast.backendAPI.exception.EmailOrUsernameAlreadyTakenException;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.security.auth_payload.AuthenticationRequest;
import com.quizztoast.backendAPI.security.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth_payload.VerificationRequest;
import com.quizztoast.backendAPI.security.auth_service.AuthenticationService;
import com.quizztoast.backendAPI.security.recaptcha.ReCaptchaRegisterService;
import com.quizztoast.backendAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final ReCaptchaRegisterService reCaptchaRegisterService;
    private final UserService userService;

    /**
     * register new User using a Post request.
     *
     * @return A message indicating the success of the Post operation.
     */
    @Operation(
            summary = "Registers a new user",
            description = "Registers a new user with the provided details.",
            responses = {
                    @ApiResponse(
                            description = "Success. User registered successfully.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = " {\n" +
                                                            "    \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmhhbmFuaDEyM0BnbWFpbC5jb20iLCJpYXQiOjE3MDU5NDkzODYsImV4cCI6MTcwNjAzNTc4Nn0.GbOt25veZBXl3YHwJrW101CT-gvNGC20RTQK4uBwdAk\",\n" +
                                                            "    \"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmhhbmFuaDEyM0BnbWFpbC5jb20iLCJpYXQiOjE3MDU5NDkzODYsImV4cCI6MTcwNjU1NDE4Nn0.BJAeEOcrPjd23_PlJfoMtkq345yxnRhv0eHoObNyjfo\",\n" +
                                                            "    \"mfaEnabled\": true,\n" +
                                                            "    \"secretImageUri\": \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAV4AAAFeAQAAAADlUEq3AAADHElEQVR4Xu2asZHjMAxFoXFw4ZagUlSaWJpKcQkOHXiMw/+AxtSK9vji+wiWMvnI4C9AALLNv7eH/Z75YIJ7E9yb4N4E9/Z/wE+j+eOPX/GAYY2lH99s8jsXL4KHMJ68PS93LBsGfPKb2crJQgQP4NByaaHzbd5Wzm9GOCb9/hPiC/4IQ9Lr0sJFb7PHYBZbBX8Dxweb3RnPi0+I9diKEwS/h+NPxHMqi4sxPtVWiL8jgs+w0cpFTwMXBQ/hg7XJPe9Hempvgnt76VyBvLSpShi4KAYmmknwEI6/cfmtASOeWQTymoytiG7I/YpuwT2cE3Exgko46udILSGw5/1IE3yC0XM445kuyug2qB4Cx40IFxU8hp+cQIMGp2TzVqobz4rb8tC1Ce7TMaKblx/m6bC5tRnsEN2COxh2jbnq0/KaDJ2j4eXyteQWfIbhlIbCGe0agxzLyDD0VD+mY8EvmC6aNyJ0Dosgx2QUNPi0D4JPsGfFbFzOgiaWeT/WQUgtggdw6ZwljNEboWxWMnkQTxR8hi98H4AXK54FjWOZ0Q2/nSG+4DdwuKcx5UY6DpszK88pcIiPQfAIdghcPQfvR3hqVjI4aElE8Al2zMc6PRU6Y4D4cw7W6yz44HW7wFQWg2cjgq3Yg5AXPIaNzcaDAu855YHZvBG3jHzBJziKlvySEc+tghxUqV6fBJ/h0hmU4zsgGru2pfGEX62H4Bd8oZaeLlpOiUrGqnA+RLfgHk6dEd3RZTjCetlPyLfxoASP4Mwb6ZvN8v1KRbehLTlEt+AD7Hi/MrH6W1HXxB7elpDb4aI8R/AY5kS9oQpxV7po/WIAJwh+B8c6vmu0fEOVlQz63j3fHFxU8A6XPdFzbCs6EArMsIaLxvBKx4J7mFrmryxC4KQwj+iurTTBZxj+F1dhlsp4LpixTp0Fv4PviGBUzLEMnbmWnS7+AxXygj/AeP+OrBx7WMmU3BuDXPBH2EvnSM6sZFrnt4JHMB5RwsQduNeCDOslJw85RfAow3LZ0GwsnJyrH9lACR7A35rg3gT3Jrg3wb0J7u3f4L/kiTr3Hk1oVAAAAABJRU5ErkJggg==\"\n" +
                                                            "}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Conflict. User with the provided telephone or username or email already exists.",
                            responseCode = "422",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "Email",
                                                                        "errorMessage": " Email already taken"
                                                                    }
                                                                ],
                                                                "message": "Validation Failed",
                                                                "error": true
                                                            }"""
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided.",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"data\":[{\"fieldName\":\"email\",\"errorMessage\":\"email must be a gmail account\"}],\"message\":\"Validation Failed\",\"error\":true}"
                                            )
                                    })
                    ), @ApiResponse(
                    description = "",
                    responseCode = "404",
                    content = @Content
            )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            validateEmail(request.getEmail());
            ValidateUsername(request.getUsername());
            ValidatePhoneNumber(request.getTelephone());
            AuthenticationResponse response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (EmailAlreadyTakenException e) {
            throw new EmailOrUsernameAlreadyTakenException("Email", " Email already taken");
        }
    }

    private void ValidatePhoneNumber(String telephone) {
        if (userService.checkTelephone(telephone).isPresent()) {
            throw new EmailOrUsernameAlreadyTakenException("telephone", " telephone already taken");
        }
    }

    private void ValidateUsername(String username) {
        if (userService.checkUsername(username).isPresent()) {
            throw new EmailOrUsernameAlreadyTakenException("username", " username already taken");
        }
    }

    private void validateEmail(String email) {
        if (userService.userExists(email)) {
            throw new EmailOrUsernameAlreadyTakenException("Email", " Email already taken");
        }
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
,  content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "    \"mfaEnabled\": true\n" +
                                                    "}"
                                    )
                            })
                    ),
                    @ApiResponse(
                            description = "Success. User authenticated successfully.",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"email\",\n" +
                                                            "            \"errorMessage\": \"Invalid email address\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
                    )
                    ,
                    @ApiResponse(
                            description = "Success. User authenticated successfully.",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"mfaEnabled\": false,\n" +
                                                            "    \"error\": \"Incorrect email or password\"\n" +
                                                            "}"
                                            )
                                    })
                    )
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

    /**
     * refresh-token using a Post request.
     *
     * @return A message indicating the success of the Post operation.
     */
    @Operation(
            summary = "Refreshes the authentication token",
            description = "Refreshes the authentication token using the provided refresh token.",
            responses = {
                    @ApiResponse(
                            description = "Success. Token refreshed successfully.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"userId\":10552,\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAZ21haWwuY29tIiwiaWF0IjoxNzA1ODYyNTMxLCJleHAiOjE3MDU5NDg5MzF9.SetWXiiWdKwvzuAmsNPgHG0N8x_n4b06q_L4fbjwg4w\",\"refreshToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAZ21haWwuY29tIiwiaWF0IjoxNzA1ODU4OTIzLCJleHAiOjE3MDU5NDUzMjN9.XJ3eecQzrRzz0H9hFZICV2QCPXnsSFLUd6732YlbDRc\",\"mfaEnabled\":false}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid refresh token provided.",
                            responseCode = "400"
                    )
                    ,
                    @ApiResponse(
                            description = "Bad Request. Invalid refresh token provided.",
                            responseCode = "404"
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

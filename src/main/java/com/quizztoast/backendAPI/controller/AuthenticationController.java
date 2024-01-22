package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.exception.EmailAlreadyTakenException;
import com.quizztoast.backendAPI.security.auth_payload.AuthenticationRequest;
import com.quizztoast.backendAPI.security.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth_payload.VerificationRequest;
import com.quizztoast.backendAPI.security.auth_service.AuthenticationService;
import com.quizztoast.backendAPI.security.recaptcha.ReCaptchaRegisterService;
import com.quizztoast.backendAPI.security.recaptcha.ReCaptchaResponse;
import com.quizztoast.backendAPI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register (
//            @Valid
//            @RequestBody RegisterRequest request
////            @RequestParam(name = "g-recaptcha-response") String captcha,
////            Model model
//    ){
////        ReCaptchaResponse reCaptchaResponse = reCaptchaRegisterService.verifyCaptcha(captcha);
////        if(!reCaptchaResponse.isSuccess()){
////            model.addAttribute("reCaptchaError", reCaptchaResponse.getErrorCodes());
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
////        }
//        return ResponseEntity.ok(authenticationService.register(request));
//    }
@PostMapping("/register")
public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    try {
        validateEmail(request.getEmail());

        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    } catch (EmailAlreadyTakenException e) {
        return ResponseEntity.badRequest().body(createErrorResponse("email", e.getMessage()));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("error", "An error occurred during registration"));
    }
}

    private void validateEmail(String email) {
        if (userService.userExists(email)) {
            throw new EmailAlreadyTakenException("Email already taken");
        }
    }

    private Map<String, String> createErrorResponse(String key, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(key, message);
        return errorResponse;
    }

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

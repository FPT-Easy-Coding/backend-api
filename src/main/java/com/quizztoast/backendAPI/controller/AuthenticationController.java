package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.security.auth.auth_payload.AuthenticationRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.VerificationRequest;
import com.quizztoast.backendAPI.security.auth.auth_service.AuthenticationService;
import com.quizztoast.backendAPI.security.recaptcha.ReCaptchaRegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final ReCaptchaRegisterService reCaptchaRegisterService;

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
public ResponseEntity<?> register(
        @Valid
        @RequestBody RegisterRequest request
) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);

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

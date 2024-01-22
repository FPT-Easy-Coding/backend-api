package com.quizztoast.backendAPI.security.recaptcha;

public interface ReCaptchaService {
    ReCaptchaResponse verifyCaptcha(String gRecaptchaResponse);
}

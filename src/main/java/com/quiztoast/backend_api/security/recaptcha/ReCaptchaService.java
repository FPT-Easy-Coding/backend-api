package com.quiztoast.backend_api.security.recaptcha;

public interface ReCaptchaService {
    ReCaptchaResponse verifyCaptcha(String gRecaptchaResponse);
}

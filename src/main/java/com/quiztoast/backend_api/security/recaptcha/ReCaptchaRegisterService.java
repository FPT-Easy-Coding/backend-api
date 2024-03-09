package com.quiztoast.backend_api.security.recaptcha;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ReCaptchaRegisterService implements ReCaptchaService {
    private final ReCaptchaKeys reCaptchaKeys;
    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(ReCaptchaRegisterService.class);
    @Override
    public ReCaptchaResponse verifyCaptcha(String gRecaptchaResponse) {
        //API request
        URI verifyUri = URI.create(
                String.format(
                        "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",
                        reCaptchaKeys.getSecretKey(), gRecaptchaResponse));

        //Make HTTP call using the RestTemplate
        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(verifyUri, ReCaptchaResponse.class);

        //Log the return reCaptchaResponse object
        log.info("ReCaptcha response after verifying: {}", reCaptchaResponse);
        if (reCaptchaResponse != null){
            if(!reCaptchaResponse.isSuccess() && (reCaptchaResponse.getScore() < reCaptchaKeys.getThreshold())
            || !reCaptchaResponse.getAction().equals("register")) {
               reCaptchaResponse.setSuccess(false);
            }
        }
        return reCaptchaResponse;
    }
}

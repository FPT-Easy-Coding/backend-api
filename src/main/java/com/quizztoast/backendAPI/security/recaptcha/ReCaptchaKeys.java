package com.quizztoast.backendAPI.security.recaptcha;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReCaptchaKeys {

    private String siteKey;
    private String secretKey;
    private float threshold;
}

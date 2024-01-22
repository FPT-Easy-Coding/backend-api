package com.quizztoast.backendAPI.security.recaptcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReCaptchaResponse {

    private boolean success;
    private float score;
    private String hostname;
    private String action;

    @JsonProperty("challenge_ts")
    private String challenge_ts;
    @JsonProperty("error-codes")
    List<String> errorCodes;

    public List<String> getErrorCodes() {
        if(getErrorCodes() != null){
            return getErrorCodes().stream()
                    .map(ReCaptchaErrorCodes.RECAPTCHA_ERROR_CODES::get)
                    .collect(Collectors.toList());
        }
        return null;
    }
}

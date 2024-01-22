package com.quizztoast.backendAPI.security.auth_payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticationResponse {
    private Long userId;
//    @JsonProperty("access_token")
    private String accessToken;
//    @JsonProperty("refresh_token")
    private String refreshToken;
    private boolean mfaEnabled;
    private String secretImageUri;
    private String error;

    public AuthenticationResponse(String err) {
        this.error = err;
    }
}

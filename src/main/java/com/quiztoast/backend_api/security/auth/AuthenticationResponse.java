package com.quiztoast.backend_api.security.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quiztoast.backend_api.model.entity.user.Role;
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
    private boolean isBanned;
    private Role role;

}

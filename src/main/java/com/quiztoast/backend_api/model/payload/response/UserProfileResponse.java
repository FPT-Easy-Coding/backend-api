package com.quiztoast.backend_api.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private com.quiztoast.backend_api.model.entity.user.Role Role;
    private boolean isBanned;
    private boolean isPremium;
}

package com.quizztoast.backendAPI.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quizztoast.backendAPI.model.entity.user.Role;
import lombok.*;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private Role Role;
    private boolean isBanned;
    private boolean isPremium;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    private String avatar;
    private String accountType;
}

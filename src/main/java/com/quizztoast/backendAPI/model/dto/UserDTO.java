package com.quizztoast.backendAPI.model.dto;

import com.quizztoast.backendAPI.model.entity.user.Role;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private Role Role;
    private boolean isBanned;
    private boolean isPremium;
    private Date createdAt;

    public UserDTO(String username, String firstName, String lastName, String email, String telephone) {
    }
}

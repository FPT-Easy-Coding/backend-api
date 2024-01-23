package com.quizztoast.backendAPI.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
}

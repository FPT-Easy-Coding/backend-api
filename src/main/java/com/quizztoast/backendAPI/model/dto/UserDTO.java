package com.quizztoast.backendAPI.model.dto;

import com.quizztoast.backendAPI.model.entity.user.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Valid

    @NotNull(message = "firstname cannot be null")
    @NotBlank(message = "firstname cannot be blank")
    private String firstname;

        @NotNull(message = "lastname cannot be null")
    @NotBlank(message = "lastname cannot be blank")
    private String lastname;

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be a valid email")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*@gmail\\.com$",
            message = "email must be a gmail account"
    )

    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    @Size(min = 5, max=20,
            message = "password must be between 5 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$",
            message = "password must contain at least one uppercase letter, one number and one special character"
    )
    private String password;

    @NotNull(message = "telephone cannot be null")
    @NotBlank(message = "telephone cannot be blank")
    @Pattern(
            regexp = "^[0-9\\s\\-]+$",
            message = "telephone must be a valid phone number"
    )
    private String telephone;

    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    @Size(min = 5, max = 20,
            message = "username must be between 5 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9_\\-]+$",
            message = "username must only contain letters, numbers, hyphens and underscores"
    )
    private String username;
    private Date createdAt;
    private boolean isBanned;
    private String googleId;
    private Role role;
    private boolean isPremium;
    private boolean mfaEnabled;
    private String secret;

}

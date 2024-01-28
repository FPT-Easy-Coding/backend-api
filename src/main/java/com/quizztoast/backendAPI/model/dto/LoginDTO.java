package com.quizztoast.backendAPI.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @Valid
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
}

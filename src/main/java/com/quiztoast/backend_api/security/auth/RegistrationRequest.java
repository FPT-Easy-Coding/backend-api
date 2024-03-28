package com.quiztoast.backend_api.security.auth;

import com.quiztoast.backend_api.exception.annotation.email_exits.EmailExists;
import com.quiztoast.backend_api.model.entity.user.Role;
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
public class RegistrationRequest {
    @Valid

    @NotNull(message = "firstname cannot be null")
    @NotBlank(message = "firstname cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "Firstname must contain only letters and one space between words")


    private String firstName;

    @NotNull(message = "lastname cannot be null")
    @NotBlank(message = "lastname cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "Firstname must contain only letters and one space between words")


    private String lastName;

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be a valid email")
    @EmailExists(message = "email already exists")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    @Size(min = 5, max = 20,
            message = "password must be between 5 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).*",
            message = "Password must contain at least one uppercase letter, one number, and one special character."
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
    private String userName;

    private final Role role = Role.USER;

    private boolean mfaEnabled;


}

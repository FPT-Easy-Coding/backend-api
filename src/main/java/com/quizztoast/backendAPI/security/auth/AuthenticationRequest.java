package com.quizztoast.backendAPI.security.auth;
import com.quizztoast.backendAPI.exception.annotation.credentials.ValidCredentials;
import com.quizztoast.backendAPI.exception.annotation.email_exits.EmailExists;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidCredentials(fields = {"email", "password"}, message = "Wrong email or password")
public class AuthenticationRequest {
    @Valid
    @NotNull(message = "Email is not null")
    @NotBlank(message = "Email is not blank")
    @Email(message = "Invalid email address")
    private String email;
    @Valid
    @NotNull(message = "Password is not null")
    @NotBlank(message = "Password is not blank")
    private String password;
}

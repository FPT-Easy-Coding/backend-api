package com.quizztoast.backendAPI.exception.annotation.credentials;

import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth.auth_payload.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CredentialsValidator implements ConstraintValidator<ValidCredentials, AuthenticationRequest> {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(ValidCredentials constraintAnnotation) {
    }

    @Override
    public boolean isValid(AuthenticationRequest request, ConstraintValidatorContext context) {
        // Implement your password validation logic here
        return request != null && isValidPassword(request.getEmail(), request.getPassword());
    }

    private boolean isValidPassword(String email, String enteredPassword) {
        // Fetch the user entity from the database based on the email
        User user = userRepository.findByEmail(email).orElse(null);

        // Check if the user exists and the entered password matches the stored hashed password
        return user != null && passwordEncoder.matches(enteredPassword, user.getPassword());
    }
}

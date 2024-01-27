package com.quizztoast.backendAPI.exception.annotation.email_exits;

import com.quizztoast.backendAPI.service.impl.UserServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validator class for checking if an email already exists in the system.
 * This class is annotated with {@link EmailExists} and implements
 * {@link ConstraintValidator} to provide validation logic for email uniqueness.
 *
 */
public class EmailExistsValidator implements ConstraintValidator<EmailExists, String> {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Initializes the EmailExistsValidator.
     *
     * @param constraintAnnotation the EmailExists constraint annotation (not used in this implementation)
     */
    @Override
    public void initialize(EmailExists constraintAnnotation) {
        // Initialization logic, if needed (not used in this implementation)
    }

    /**
     * Checks if the given email is valid by verifying its uniqueness.
     *
     * @param email   the email to be validated
     * @param context additional context for the validation
     * @return true if the email is valid (not already exists), false otherwise
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Validation logic: Returns true if the email is valid (not already exists), false otherwise
        return email == null || !userServiceImpl.userExists(email);
    }
}

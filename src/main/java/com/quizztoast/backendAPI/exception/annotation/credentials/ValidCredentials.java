package com.quizztoast.backendAPI.exception.annotation.credentials;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CredentialsValidator.class})
@Documented
public @interface ValidCredentials {

    String message() default "Invalid credentials";

    String[] fields() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

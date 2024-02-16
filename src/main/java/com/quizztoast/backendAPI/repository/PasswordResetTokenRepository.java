package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.token.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}

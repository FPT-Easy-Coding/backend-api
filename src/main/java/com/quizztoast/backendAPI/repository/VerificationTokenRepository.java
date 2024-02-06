package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}

package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    @Modifying
    @Query("DELETE FROM VerificationToken t WHERE t.user.userId = :userId")
    void deleteTokensByUserId(Long userId);
}

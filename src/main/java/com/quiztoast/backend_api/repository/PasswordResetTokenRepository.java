package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.token.PasswordResetToken;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    @Modifying
    @Query("delete from PasswordResetToken t where t.user = :user")
    void deleteByUser(User user);
}

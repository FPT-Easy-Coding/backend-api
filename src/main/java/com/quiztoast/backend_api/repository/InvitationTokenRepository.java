package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.token.InvitationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationTokenRepository extends JpaRepository<InvitationToken, Long> {
    InvitationToken findByToken(String token);

    @Modifying
    @Query("DELETE FROM InvitationToken t WHERE t.user.userId = :userId")
    void deleteTokensByUserId(Long userId);
}

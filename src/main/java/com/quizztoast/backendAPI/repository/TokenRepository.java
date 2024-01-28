package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.token.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
select t from  Token t inner  join  User  u on t.user.userId = u.userId
where  u.userId = :userId and  (t.expired = false or t.revoked = false )
""")
    public List<Token> findAllValidTokensByUser(long userId);
    Optional <Token> findByToken (String token);
    @Transactional
    @Modifying
    @Query("DELETE FROM Token t WHERE t.user.userId = :userId")
    void deleteTokenByUserId(Long userId);

}

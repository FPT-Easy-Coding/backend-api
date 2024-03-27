package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.user.Provider;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by email.
     *
     * @param  email  the email to search for
     * @return       an optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by ID.
     *
     * @param  userId  the ID of the user to find
     * @return          an optional containing the user, or empty if not found
     */
    User findByUserId(Long userId);
    User findUserByEmail(String email);
    Optional<User> findByUserName(String userName);

    @Modifying
    @Query("UPDATE User u SET u.provider = ?2 WHERE u.userName = ?1")
    void updateAuthenticationType(String userName, Provider provider);
}

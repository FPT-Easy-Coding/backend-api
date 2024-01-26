package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.user.AuthenticationType;
import com.quizztoast.backendAPI.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

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
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.authType = ?2 WHERE u.username = ?1")
    public void updateAuthenticationType(String username, AuthenticationType authType);
}

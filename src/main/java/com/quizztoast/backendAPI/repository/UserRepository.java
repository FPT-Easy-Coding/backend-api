package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

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

    Optional<User> findByUsername(String username);
}

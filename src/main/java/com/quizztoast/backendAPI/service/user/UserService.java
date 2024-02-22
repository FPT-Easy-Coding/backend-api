package com.quizztoast.backendAPI.service.user;

import com.quizztoast.backendAPI.model.entity.token.VerificationToken;
import com.quizztoast.backendAPI.model.entity.user.User;

import com.quizztoast.backendAPI.model.payload.request.ChangePasswordRequest;
import com.quizztoast.backendAPI.security.auth.RegistrationRequest;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    User createUser(RegistrationRequest user);
    List<User> getAllUsers();
    User getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    User addNewUser(User user);
    void deleteUser(Long userId);
    boolean doesUserExist(Long userId);
    boolean doesEmailExist(String email);
    boolean doesUsernameExist(String username);
    boolean userExists(String email);
    void updateAuthenticationType(String username, String oauth2ClientName);

    User updateUser(Long userId, User user);

    void saveVerificationToken(User user, String verificationToken);

    String validateVerificationToken(VerificationToken verificationToken);

     void savePasswordResetTokenForUser(User user, String token);
    String validatePasswordResetToken(String token);
    Optional<User> getUserByPasswordResetToken(String token);
    void resetUserPassword(User user, String newPassword);
}

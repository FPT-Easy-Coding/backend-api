package com.quiztoast.backend_api.service.user;

import com.quiztoast.backend_api.model.entity.token.VerificationToken;
import com.quiztoast.backend_api.model.entity.user.User;

import com.quiztoast.backend_api.model.payload.request.ChangePasswordRequest;
import com.quiztoast.backend_api.model.payload.request.UserUpdateRequest;
import com.quiztoast.backend_api.security.auth.RegistrationRequest;

import org.springframework.http.ResponseEntity;

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
    ResponseEntity<?> getProfileUserCreateQuiz(int quizId);
    ResponseEntity<UserUpdateRequest> updateProfileUser(long userId,UserUpdateRequest request);

    ResponseEntity<?> updateAvatarUser(long userId, String avatar);
}

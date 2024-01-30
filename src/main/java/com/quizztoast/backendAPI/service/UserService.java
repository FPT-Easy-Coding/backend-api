package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.entity.user.User;

import com.quizztoast.backendAPI.security.auth.auth_payload.ChangePasswordRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    User createUser(RegisterRequest user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User addNewUser(User user);
    void deleteUser(Long userId);
    boolean doesUserExist(Long userId);
    boolean doesEmailExist(String email);
    boolean doesUsernameExist(String username);
    boolean userExists(String email);
    void updateAuthenticationType(String username, String oauth2ClientName);

    User updateUser(Long userId, User user);
}

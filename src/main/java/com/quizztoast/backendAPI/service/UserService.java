package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizCreationRequestDTO;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth.auth_payload.ChangePasswordRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    void addNewUser(User user);
    void deleteUser(int userId);
    boolean doesUserExist(int userId);
    boolean doesEmailExist(String email);
    boolean doesUsernameExist(String username);
    ResponseEntity<QuizCreationRequestDTO> createQuizQuestionAndAnswers(QuizCreationRequestDTO requestDTO);
    ResponseEntity<String> updateQuizAnswer(Long answerId, QuizAnswerDTO quizAnswerDTO);
    ResponseEntity<String> deleteQuizQuesById(Long quizquestionId);
    boolean userExists(String email);
    void updateAuthenticationType(String username, String oauth2ClientName);
}

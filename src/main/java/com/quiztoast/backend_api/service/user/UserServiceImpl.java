package com.quiztoast.backend_api.service.user;

import com.quiztoast.backend_api.exception.FormatException;
import com.quiztoast.backend_api.model.dto.UserDTO;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.token.PasswordResetToken;
import com.quiztoast.backend_api.model.entity.token.VerificationToken;
import com.quiztoast.backend_api.model.entity.user.Provider;
import com.quiztoast.backend_api.model.entity.user.User;

import com.quiztoast.backend_api.model.mapper.QuizMapper;
import com.quiztoast.backend_api.model.payload.response.QuizSetResponse;
import com.quiztoast.backend_api.repository.*;
import com.quiztoast.backend_api.model.payload.request.ChangePasswordRequest;
import com.quiztoast.backend_api.security.auth.RegistrationRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.quiztoast.backend_api.model.mapper.UserMapper.mapUserDtoToAdmin;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final QuizRepository quizRepository;
    private final CreateQuizCategoryRepository createQuizCategoryRepository;
    private final QuizQuestionMappingRepository quizQuestionMappingRepository;
    private final UserBelongClassroomRepository userBelongClassroomRepository;
    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        //Check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        //Check if 2 password are matched
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


    @Override
    public User createUser(RegistrationRequest user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setTelephone(user.getTelephone());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setMfaEnabled(user.isMfaEnabled());
        newUser.setProvider(Provider.LOCAL);
        newUser.setCreatedAt(new Date());
        return userRepository.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findByUserId(id);
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        return user;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addNewUser(User user) {
        User newUser = new User();
        newUser.setUserName(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setCreatedAt(new Date());
        newUser.setProvider(Provider.LOCAL);
        newUser.setTelephone(user.getTelephone());
        newUser.setPremium(user.isPremium());
        newUser.setBanned(user.isBanned());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long userId) {
        tokenRepository.deleteTokensByUserId(userId);
        verificationTokenRepository.deleteTokensByUserId(userId);
        userBelongClassroomRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public boolean doesUserExist(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.isPresent();
    }

    @Override
    public boolean doesEmailExist(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    @Override
    public boolean doesUsernameExist(String username) {
        Optional<User> existingUser = userRepository.findByUserName(username);
        return existingUser.isPresent();
    }


    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    public void updateAuthenticationType(String username, String oauth2ClientName) {
        Provider authType = Provider.valueOf(oauth2ClientName.toUpperCase());
        userRepository.updateAuthenticationType(username, authType);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findByUserId(userId);
        if (existingUser == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        existingUser.setUserName(user.getUserName());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setTelephone(user.getTelephone());
        existingUser.setRole(user.getRole());
        existingUser.setBanned(user.isBanned());
        existingUser.setPremium(user.isPremium());
        return userRepository.save(existingUser);
    }

    @Override
    public void saveVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(VerificationToken token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token.getToken());
        if (verificationToken == null) {
            return "invalid reset password token";
        }
        User user = verificationToken.getUser();
        if ((verificationToken.getTokenExpirationTime().compareTo(new Date())) < 0) {
            verificationTokenRepository.delete(verificationToken);
            return "Token already expired";
        }
        user.setVerified(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return "valid";
    }

    @Override
    public void savePasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return "invalid verification token";
        }
        User user = passwordResetToken.getUser();
        if ((passwordResetToken.getTokenExpirationTime().compareTo(new Date())) < 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "Token already expired";
        }
        userRepository.save(user);
//        passwordResetTokenRepository.delete(passwordResetToken);
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> getProfileUserCreateQuiz(int quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        //get userDTO by quizId
        User user = userRepository.findByUserId(createQuizCategoryRepository.findUserId(quizId));
        UserDTO userDTO = mapUserDtoToAdmin(user);
        return ResponseEntity.ok(userDTO);
    }

    public List<QuizSetResponse> getCreatedQuizByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        List<Quiz> quizSets = quizRepository.findQuizByUserId(userId);
        List<QuizSetResponse> quizSetResponses = new ArrayList<>();
        for (Quiz quiz : quizSets) {
            int numberOfQuestions = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
            QuizSetResponse response = QuizMapper.mapQuizToQuizSetResponse(quiz,numberOfQuestions);
            quizSetResponses.add(response);
        }
        return quizSetResponses;
    }
}

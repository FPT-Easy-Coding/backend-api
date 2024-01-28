package com.quizztoast.backendAPI.service.impl;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizCreationRequestDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.entity.quiz.QuizAnswer;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.entity.user.Provider;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.repository.QuizAnswerRepository;
import com.quizztoast.backendAPI.repository.QuizQuestionRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth.auth_payload.ChangePasswordRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.service.CategoryService;
import com.quizztoast.backendAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, QuizQuestionRepository quizQuestionRepository,
                           QuizAnswerRepository quizAnswerRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        //Check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new IllegalStateException("Wrong password");
        }
        //Check if 2 password are matched
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User createUser(RegisterRequest user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstname());
        newUser.setLastName(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setTelephone(user.getTelephone());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setMfaEnabled(user.isMfaEnabled());
        newUser.setProvider(Provider.LOCAL);
        return userRepository.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User user =  userRepository.findByUserId(id);
        if(user == null){
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user =  userRepository.findUserByEmail(email);
        return user;
    }

    @Override
    public void addNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById((long) userId);
    }

    @Override
    public boolean doesUserExist(int userId) {
        Optional<User> userOptional = userRepository.findById((long) userId);
        return userOptional.isPresent();
    }

    @Override
    public boolean doesEmailExist(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    @Override
    public boolean doesUsernameExist(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    public ResponseEntity<QuizCreationRequestDTO> createQuizQuestionAndAnswers(QuizCreationRequestDTO requestDTO) {

        // Kiểm tra category_id tồn tại trong bảng category
        ResponseEntity<String> categoryCheckResult = categoryService.existsCategoryById(requestDTO.getCategoryId());

        if (categoryCheckResult.getStatusCode() == HttpStatus.NOT_FOUND) {
            String errorMessage = categoryCheckResult.getBody();
            return ResponseEntity.<QuizCreationRequestDTO>status(HttpStatus.NOT_FOUND).body(null);
        }

        Category category = categoryRepository.findById(requestDTO.getCategoryId()).orElse(null);

        try {
            // Tạo QuizQuestion
            QuizQuestion quizQuestion = QuizQuestion.builder()
                    .content(requestDTO.getQuestionContent())
                    .category_id(category)
                    .created_at(LocalDateTime.now())
                    .build();

            quizQuestionRepository.save(quizQuestion);

            // Tạo danh sách QuizAnswer
            List<QuizAnswer> quizAnswers = new ArrayList<>();
            for (QuizAnswerDTO answerDTO : requestDTO.getAnswers()) {
                QuizAnswer quizAnswer = QuizAnswer.builder()
                        .content(answerDTO.getContent())
                        .is_correct(answerDTO.isCorrect())
                        .created_at(LocalDateTime.now())
                        .quizQuestion(quizQuestion)
                        .build();

                quizAnswers.add(quizAnswer);
            }

            // Lưu danh sách QuizAnswer
            quizAnswerRepository.saveAll(quizAnswers);

            // Trả về QuizCreationRequestDTO ban đầu sau khi đã tạo
            return ResponseEntity.ok(requestDTO);
        } catch (Exception e) {
            return ResponseEntity.<QuizCreationRequestDTO>status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<String> updateQuizAnswer(Long answerId, QuizAnswerDTO quizAnswerDTO) {
        Optional<QuizAnswer> optionalQuizAnswer = quizAnswerRepository.findById(answerId);

        if (optionalQuizAnswer.isPresent()) {
            QuizAnswer quizAnswer = optionalQuizAnswer.get();

            // Kiểm tra và cập nhật nội dung
            if (quizAnswerDTO.getContent() != null && !quizAnswerDTO.getContent().isEmpty()) {
                quizAnswer.setContent(quizAnswerDTO.getContent());
            }

            // Kiểm tra và cập nhật đáp án đúng/sai
            quizAnswer.setIs_correct(quizAnswerDTO.isCorrect());

            // Lưu cập nhật
            quizAnswerRepository.save(quizAnswer);

            return ResponseEntity.ok("QuizAnswer updated successfully");
        } else {
            // Trả về lỗi nếu không tìm thấy ID
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QuizAnswer with ID " + answerId + " not found");
        }
    }

    public ResponseEntity<String> deleteQuizQuesById(Long quizquestionId) {
        //delete in quizanswer
        quizAnswerRepository.deleteById(quizquestionId);
        //delete in quizQuestion
        quizQuestionRepository.deleteById(quizquestionId);

        return ResponseEntity.ok("Delete Succesfull");
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
}

package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.dto.QuizCreationRequestDTO;
import com.quizztoast.backendAPI.model.quiz.Category;
import com.quizztoast.backendAPI.model.quiz.QuizAnswer;
import com.quizztoast.backendAPI.model.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.repository.QuizAnswerRepository;
import com.quizztoast.backendAPI.repository.QuizQuestionRepository;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth.auth_payload.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityNotFoundException;
import com.quizztoast.backendAPI.security.auth.auth_payload.ChangePasswordRequest;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser){

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
    public User createUser(RegisterRequest user){
        User newUser = new User();
        newUser.setFirstName(user.getFirstname());
        newUser.setLastName(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setTelephone(user.getTelephone());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setMfaEnabled(user.isMfaEnabled());
        return userRepository.save(newUser);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        User user =  userRepository.findByUserId(id);
        if(user == null){
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        return user;
    }
    public void addNewUser(@RequestBody User user) {
        userRepository.save(user);
    }


    public void deleteUser(int userId) {
        userRepository.deleteById((long) userId);
    }

    public boolean doesUserExist(int userId) {
        // Implement logic to check if the user with the given ID exists
        // For example, you might check if a user with the provided ID is present in your repository
        Optional<User> userOptional = userRepository.findById((long) userId);
        return userOptional.isPresent();
    }

    public boolean doesEmailExist(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

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

    public boolean userExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }
    public String getHashedPasswordByEmail(String email) {
        // Fetch the user entity from the database based on the email
        User user = userRepository.findByEmail(email).orElse(null);

        // Check if the user exists
        if (user != null) {
            // Return the hashed password from the user entity
            return user.getPassword();
        } else {
            // Handle the case where the user doesn't exist
            return null;
        }
    }
}


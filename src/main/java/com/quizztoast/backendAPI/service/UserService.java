package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.dto.QuizCreationRequestDTO;
import com.quizztoast.backendAPI.dto.UserDTO;
import com.quizztoast.backendAPI.exception.EmailOrUsernameAlreadyTakenException;
import com.quizztoast.backendAPI.model.quiz.Category;
import com.quizztoast.backendAPI.model.quiz.QuizAnswer;
import com.quizztoast.backendAPI.model.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.user.User;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.repository.QuizAnswerRepository;
import com.quizztoast.backendAPI.repository.QuizQuestionRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth_payload.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
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
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new EmailOrUsernameAlreadyTakenException("currentPassword", "currentPassword :" + " incorrect");
        }
        //Check if 2 password are matched
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new EmailOrUsernameAlreadyTakenException("ConfirmPassword", "ConfirmPassword :" + " incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public void addNewUser(@RequestBody User user) {
        userRepository.save(user);
    }

    public boolean userExists(String email){
        return userRepository.findByEmail(email).isPresent();
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


//        if ( categoryService.existsCategoryById(requestDTO.getCategoryId())) {
//            throw new EmailOrUsernameAlreadyTakenException("CategoryId", "currentPassword :" + " incorrect");
//        }
        // Kiểm tra category_id tồn tại trong bảng category
        ResponseEntity<String> categoryCheckResult = categoryService.existsCategoryById(requestDTO.getCategoryId());

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
                        .is_correct(answerDTO.getIsCorrect())
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

    public ResponseEntity<QuizAnswer> updateQuizAnswer(Long answerId, @Valid QuizAnswerDTO quizAnswerDTO) {

        // Check if the answerId exists in the repository
        Optional<QuizAnswer> optionalQuizAnswer = quizAnswerRepository.findById(answerId);

        if (optionalQuizAnswer.isPresent()) {
            QuizAnswer quizAnswer = optionalQuizAnswer.get();


            // Kiểm tra và cập nhật nội dung
//            if (quizAnswerDTO.getContent() != null && !quizAnswerDTO.getContent().isEmpty()) {
//                quizAnswer.setContent(quizAnswerDTO.getContent());
//            }

            // Kiểm tra và cập nhật đáp án đúng/sai
           quizAnswer.setIs_correct(quizAnswerDTO.getIsCorrect());
            quizAnswer.setContent(quizAnswerDTO.getContent());
            // Lưu cập nhật
            quizAnswerRepository.save(quizAnswer);

            return ResponseEntity.ok(quizAnswer);
        } else {
            // Trả về lỗi nếu không tìm thấy ID
            throw new EmailOrUsernameAlreadyTakenException("answerId", "answerId " + answerId + " Not Found");
        }
    }

    public ResponseEntity<String> deleteQuizQuesById(Long quizquestionId) {

        // Check if the quiz question with the given ID exists in the repository
        if (quizQuestionRepository.existsById(quizquestionId)) {
            // Delete associated quiz answers
            quizAnswerRepository.deleteByQuizQuestionId(quizquestionId);

            // Delete the quiz question
            quizQuestionRepository.deleteById(quizquestionId);

            return ResponseEntity.ok("Delete Successful");
        }

        throw new EmailOrUsernameAlreadyTakenException("quizquestionId", "Quiz question with ID " + quizquestionId + " not found");

    }
    public ResponseEntity<User> UpdateUser(Long userId, @Valid UserDTO request) {

        validateUsernameOrEmail(userId, request.getUsername(), request.getEmail());
        User existingUserOptional = userRepository.findByUserId(userId);
        existingUserOptional.setUsername(request.getUsername()); // Ensure correct username update
        existingUserOptional.setPassword(request.getPassword());
        existingUserOptional.setFirstName(request.getFirstname());
        existingUserOptional.setLastName(request.getLastname());
        existingUserOptional.setEmail(request.getEmail()); // Ensure correct email update
        existingUserOptional.setTelephone(request.getTelephone());
        existingUserOptional.setBanned(request.isBanned());
        existingUserOptional.setPremium(request.isPremium());

        // Save the updated user
        userRepository.save(existingUserOptional);

        return ResponseEntity.ok(existingUserOptional);
    }

    private void validateUsernameOrEmail(Long userId, String username, String email) {
        // Kiểm tra username trùng
        Optional<User> existingUserByUsernameOptional = userRepository.findByUsername(username);
        if (existingUserByUsernameOptional.isPresent()) {
            User existingUserByUsername = existingUserByUsernameOptional.get();
            if (!existingUserByUsername.getUserId().equals(userId)) {
                throw new EmailOrUsernameAlreadyTakenException("Username","Username already taken");
            }
        }

        // Kiểm tra email trùng
        Optional<User> existingUserByEmailOptional = userRepository.findByEmail(email);
        if (existingUserByEmailOptional.isPresent()) {
            User existingUserByEmail = existingUserByEmailOptional.get();
            if (!existingUserByEmail.getUserId().equals(userId)) {
                throw new EmailOrUsernameAlreadyTakenException("Email","Email already taken");
            }
        }
    }
}


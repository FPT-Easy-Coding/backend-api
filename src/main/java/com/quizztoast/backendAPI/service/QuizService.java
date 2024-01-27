package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.repository.QuizRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {
    private  final CategoryRepository categoryRepository;
    private final QuizRepository repository;
    private final UserRepository userRepository;

    public QuizService(CategoryRepository categoryRepository, QuizRepository repository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Quiz> getAllQuiz() {
        return repository.findAll();
    }

    public ResponseEntity<QuizDTO> createQuiz(QuizDTO quizdto) {
        // Kiểm tra user_id phải có trong bảng User
        if (!userRepository.existsById(quizdto.getUser_id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Kiểm tra quiz_name phải là duy nhất trong bảng Quiz
//        if (repository.existsByQuiz_name(quizdto.getQuiz_name())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }

        // Tạo đối tượng Quiz từ QuizDTO
        Quiz quiz = new Quiz();
        quiz.setUser(userRepository.findById(quizdto.getUser_id()).orElse(null));
        quiz.setClass_id(quizdto.getClass_id());
        quiz.setCategory(categoryRepository.findById(quizdto.getCategory_id()).orElse(null));
        quiz.setQuiz_name(quizdto.getQuiz_name());
        quiz.setRate(quizdto.getRate());
        quiz.setCreated_at(LocalDateTime.now());

        // Lưu đối tượng Quiz vào bảng Quiz
        repository.save(quiz);

        // Trả về đối tượng QuizDTO sau khi đã tạo
        return ResponseEntity.ok(quizdto);
    }

}

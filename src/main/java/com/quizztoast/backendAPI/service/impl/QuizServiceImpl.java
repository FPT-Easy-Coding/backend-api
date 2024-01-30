package com.quizztoast.backendAPI.service.impl;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.mapper.QuizMapper;
import com.quizztoast.backendAPI.model.payload.Request.QuizRequest;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.repository.QuizRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

import static com.quizztoast.backendAPI.model.mapper.QuizMapper.quizToQuizDTO;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
  private final QuizRepository quizRepository;

    public QuizServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, QuizRepository quizRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public List<QuizDTO> getAllQuiz() {
        List<Quiz> listQuiz = quizRepository.findAll();
        List<QuizDTO> quizDTOList = quizToQuizDTO(listQuiz);
        return  quizDTOList;
    }

    @Override
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        // Check user_id must be in User table
        if (!userRepository.existsById(quizRequest.getUser_id())) {
            throw new FormatException("User_id","User_id not found");
        }

        // Kiểm tra quiz_name phải là duy nhất trong bảng Quiz
//        if (repository.existsByQuiz_name(quizdto.getQuiz_name())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }

        //check category
        if (!categoryRepository.existsById(quizRequest.getCategory_id())) {
            throw new FormatException("Category_id","Category_id not found");
        }
        // Create Quiz object from quizRequest
        Quiz quiz = new Quiz();
        quiz.setUser(userRepository.findById(quizRequest.getUser_id()).orElse(null));
        quiz.setCategory(categoryRepository.findById(quizRequest.getCategory_id()).orElse(null));
        quiz.setQuiz_name(quizRequest.getQuiz_name());
        quiz.setRate(quizRequest.getRate());
        quiz.setQuiz_ques_id(quizRequest.getList_question().size());
        quiz.setCreated_at(LocalDateTime.now());

        // Save the Quiz object to the Quiz table
        quizRepository.save(quiz);
        //mapper QuizDTO
        QuizDTO quizDTo = QuizMapper.mapQuizDTOToUser(quiz);
        // Returns the QuizDTO object after creation
        return ResponseEntity.ok(quizDTo);
    }

    @Override
    public ResponseEntity<String> deleteQuizById(int quizId) {

        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
    // delete quiz
        quizRepository.deleteQuizById(quizId);

        return ResponseEntity.ok().body("Quiz deleted successfully");
    }

    @Override
    public ResponseEntity<QuizDTO> UpdateQuiz(int quizId,@Valid @RequestBody QuizRequest quizRequest) {
        // Check user_id must be in User table
        if (!userRepository.existsById(quizRequest.getUser_id())) {
            throw new FormatException("User_id","User_id not found");
        }
       //check quiz exist
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        //check category
        if (!categoryRepository.existsById(quizRequest.getCategory_id())) {
            throw new FormatException("Category_id","Category_id not found");
        }
        //update quiz
        Quiz quiz = quizRepository.getQuizById(quizId);
        quiz.setUser(userRepository.findById(quizRequest.getUser_id()).orElse(null));
        quiz.setQuiz_name(quizRequest.getQuiz_name());
        quiz.setCategory(categoryRepository.findById(quizRequest.getCategory_id()).orElse(null));
        quiz.setRate(quizRequest.getRate());
        quiz.setCreated_at(quizRequest.getCreate_at());
        //save quiz
        quizRepository.save(quiz);
        //mapper QuizDTO
        QuizDTO quizDTo = QuizMapper.mapQuizDTOToUser(quiz);
        // Returns the QuizDTO object after creation
        return ResponseEntity.ok(quizDTo);
    }

    @Override
    public List<QuizDTO> GetByContent(String content) {
        if(quizRepository.findByContent(content).isEmpty())
        {
            throw  new FormatException("quiz_name","quiz_name not exist");
        }
        List<QuizDTO> listQuizDTO =quizToQuizDTO(quizRepository.findByContent(content));
        return listQuizDTO;
    }
}

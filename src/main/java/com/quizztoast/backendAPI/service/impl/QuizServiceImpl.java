package com.quizztoast.backendAPI.service.impl;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.quiz.QuizAnswer;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestionMapping;
import com.quizztoast.backendAPI.model.mapper.QuizMapper;
import com.quizztoast.backendAPI.model.mapper.QuizQuestionMapper;
import com.quizztoast.backendAPI.model.payload.Request.QuizAnswerRequest;
import com.quizztoast.backendAPI.model.payload.Request.QuizQuestionRequest;
import com.quizztoast.backendAPI.model.payload.Request.QuizRequest;
import com.quizztoast.backendAPI.repository.*;
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
    private final QuizAnswerRepository quizAnswerRepository;

    private  final QuizQuestionRepository quizQuestionRepository;
    private final QuizQuestionMappingRepository quizQuestionMappingRepository;
    public QuizServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, QuizRepository quizRepository, QuizAnswerRepository quizAnswerRepository, QuizQuestionRepository quizQuestionRepository, QuizQuestionMappingRepository quizQuestionMappingRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizQuestionMappingRepository = quizQuestionMappingRepository;
    }

    @Override
    public List<QuizDTO> getAllQuiz() {
        List<Quiz> listQuiz = quizRepository.findAll();
        return quizToQuizDTO(listQuiz);
    }

    @Override
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        // Check user_id must be in User table
        if (!userRepository.existsById(quizRequest.getUser_id())) {
            throw new FormatException("User_id", "User_id not found");
        }

        // Check category
        if (!categoryRepository.existsById(quizRequest.getCategory_id())) {
            throw new FormatException("Category_id", "Category_id not found");
        }

        // Create Quiz object from quizRequest
        Quiz quiz = new Quiz();
        quiz.setUser(userRepository.findById(quizRequest.getUser_id()).orElse(null));
        quiz.setCategory(categoryRepository.findById(quizRequest.getCategory_id()).orElse(null));
        quiz.setQuizName(quizRequest.getQuiz_name());
        quiz.setRate(quizRequest.getRate());
        quiz.setQuizQuesId(quizRequest.getList_question().size());
        quiz.setCreatedAt(LocalDateTime.now());

        // Save the Quiz object to the Quiz table
        quizRepository.saveAndFlush(quiz);

        // Iterate through quiz questions
        for (QuizQuestionRequest quizQuestionRequest : quizRequest.getList_question()) {
            // Set quizQuestion properties
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setCreatedAt(LocalDateTime.now());
            quizQuestion.setContent(quizQuestionRequest.getQuestionContent());
            quizQuestion.setCategoryId(categoryRepository.findCategoryById(quizQuestionRequest.getCategoryId()));

            // Save quizQuestion to Quiz_question table
            quizQuestionRepository.saveAndFlush(quizQuestion);

            // Iterate through quiz answers
            for (QuizAnswerRequest quizAnswerRequest : quizQuestionRequest.getAnswers()) {
                // Set quizAnswer properties
                QuizAnswer quizAnswer = new QuizAnswer();
                quizAnswer.setContent(quizAnswerRequest.getContent());
                quizAnswer.setIsCorrect(quizAnswerRequest.isCorrect());
                quizAnswer.setCreatedAt(LocalDateTime.now());

                // Set quizQuestion for quizAnswer and save
                quizAnswer.setQuizQuestion(quizQuestion);
                quizAnswerRepository.saveAndFlush(quizAnswer);
            }

            // Create QuizQuestionMapping
            QuizQuestionMapping quizQuestionMapping = new QuizQuestionMapping();
            QuizQuestionMapping.QuizQuestionMappingId id = new QuizQuestionMapping.QuizQuestionMappingId();
            id.setQuizId(quiz);
            id.setQuizQuestionId(quizQuestion);
            quizQuestionMapping.setId(id);

            // Save QuizQuestionMapping
            quizQuestionMappingRepository.save(quizQuestionMapping);
        }

        // Mapper QuizDTO
        QuizDTO quizDTO = QuizMapper.mapQuizDTOToUser(quiz);
        // Returns the QuizDTO object after creation
        return ResponseEntity.ok(quizDTO);
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
        quiz.setQuizName(quizRequest.getQuiz_name());
        quiz.setCategory(categoryRepository.findById(quizRequest.getCategory_id()).orElse(null));
        quiz.setRate(quizRequest.getRate());
        quiz.setCreatedAt(quizRequest.getCreate_at());
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
        return quizToQuizDTO(quizRepository.findByContent(content));
    }
}

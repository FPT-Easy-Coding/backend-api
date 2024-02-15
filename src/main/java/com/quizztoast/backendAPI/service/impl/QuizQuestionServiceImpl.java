package com.quizztoast.backendAPI.service.impl;


import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.quizztoast.backendAPI.exception.FormatException;

import com.quizztoast.backendAPI.model.dto.QuizQuestionDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.entity.quiz.QuizAnswer;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.mapper.QuizQuestionMapper;
import com.quizztoast.backendAPI.model.payload.Request.QuizAnswerRequest;
import com.quizztoast.backendAPI.model.payload.Request.QuizQuestionRequest;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.repository.QuizAnswerRepository;
import com.quizztoast.backendAPI.repository.QuizQuestionRepository;
import com.quizztoast.backendAPI.service.QuizQuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class QuizQuestionServiceImpl implements QuizQuestionService {

    private final CategoryRepository categoryRepository;
    private final QuizQuestionRepository quizQuestionRepository;
 private final QuizAnswerRepository quizAnswerRepository;

    public QuizQuestionServiceImpl(CategoryServiceImpl categoryServiceImpl, CategoryRepository categoryRepository, QuizQuestionRepository quizQuestionRepository, QuizAnswerRepository quizAnswerRepository) {
        this.categoryRepository = categoryRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
    }

    @Override
    public ResponseEntity<?> createQuizQuestionAndAnswers(@Valid @RequestBody QuizQuestionRequest quizRequest) {
        try {
            // Find the category by ID
            Category category = categoryRepository.findById(quizRequest.getCategoryId())
                    .orElseThrow(() -> new FormatException("categoryId", "Category not found"));

            // Create QuizQuestion
            QuizQuestion quizQuestion = QuizQuestion.builder()
                    .content(quizRequest.getQuestionContent())
                    .categoryId(category)
                    .createdAt(LocalDateTime.now())
                    .build();

            quizQuestionRepository.save(quizQuestion);

            // Create QuizAnswer list
            List<QuizAnswer> quizAnswers = new ArrayList<>();
            for (QuizAnswerRequest answerRequest : quizRequest.getAnswers()) {
                if (StringUtils.isBlank(answerRequest.getContent())) {
                    throw new FormatException("QuizAnswer", "QuizAnswer content cannot be blank");
                }
                QuizAnswer quizAnswer = QuizAnswer.builder()
                        .content(answerRequest.getContent())
                        .isCorrect(answerRequest.isCorrect())
                        .createdAt(LocalDateTime.now())
                        .quizQuestion(quizQuestion)
                        .build();

                quizAnswers.add(quizAnswer);
            }

            // Save QuizAnswer list
            quizAnswerRepository.saveAll(quizAnswers);

            // Map QuizQuestionRequest to QuizQuestionDTO
            QuizQuestionDTO quizQuestionDTO = QuizQuestionMapper.mapQuizQuesRequestToDTO(quizRequest);

            return ResponseEntity.ok(quizQuestionDTO);
        } catch (FormatException ex) {
            throw new  FormatException("categoryId", "Category not found");
            // Return the FormatException directly

        }
    }

    @Override
    public List<QuizQuestion> getAllQuiz() {
    return quizQuestionRepository.findAll();
    }
@Override
    public List<QuizQuestion> GetByContent(String content) {
        if(quizQuestionRepository.findByContentContaining(content).isEmpty())
        {
           throw  new FormatException("content","QuizQuestion_Content not exist");
        }
    return quizQuestionRepository.findByContentContaining(content);
    }

    @Override
    public ResponseEntity<QuizQuestion> UpdateQuizQuestion(int quizquestionId,@Valid @RequestBody QuizQuestionRequest quizRequest) {


        // Find the quiz question by ID
        Optional<QuizQuestion> optionalQuizQuestion = quizQuestionRepository.findById((long) quizquestionId);

        if (optionalQuizQuestion.isPresent()) {
            QuizQuestion quizQuestion = optionalQuizQuestion.get();
            //check category_id
            if(categoryRepository.findById(quizRequest.getCategoryId()).isEmpty())
            {
                throw  new FormatException("CategoryId","CategoryId not exist");
            }
            Category category = categoryRepository.findCategoryById(quizRequest.getCategoryId());

               // Update quiz question fields
               quizQuestion.setContent(quizRequest.getQuestionContent());
               quizQuestion.setCategoryId(category);
               // Save the updated quiz question
               quizQuestionRepository.save(quizQuestion);

            // Return the updated quiz question
            return ResponseEntity.ok(quizQuestion);
        } else {
            // If quiz question with the given ID is not found, return not found status
                throw  new FormatException("quizquestionId","quizquestionId not exist");

        }

    }

    @Override
    public ResponseEntity<String> deleteQuizById(Long quizquestionId) {

        // Find the quiz question by ID
        Optional<QuizQuestion> optionalQuizQuestion = quizQuestionRepository.findById((long) quizquestionId);
        if (optionalQuizQuestion.isPresent()) {
            try{
                //delete quizanswer
                quizAnswerRepository.deleteByQuizQuestionId(quizquestionId);
                //delete quizquestion
                quizQuestionRepository.deleteQuestionById(quizquestionId);

                return ResponseEntity.ok().body("Delete Succesfull QuizQuestion ");
            }catch (Exception e)
            {
                return null;
            }

        }else {
            throw  new FormatException("quizquestionId","quizquestionId not exist");
        }
    }
}

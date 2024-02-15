package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.dto.QuizQuestionDTO;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.payload.Request.QuizQuestionRequest;
import com.quizztoast.backendAPI.model.payload.Request.QuizRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface QuizQuestionService {
    ResponseEntity<?> createQuizQuestionAndAnswers( QuizQuestionRequest Quizrequest);
     List<QuizQuestion> getAllQuiz();
    List<QuizQuestion> GetByContent(String content);
    ResponseEntity<QuizQuestion> UpdateQuizQuestion(int quizquestionId, QuizQuestionRequest quizRequest);

    ResponseEntity<String> deleteQuizById(Long quizquestionId);
}

package com.quizztoast.backendAPI.service.quiz;

import com.quizztoast.backendAPI.model.dto.QuizQuestionDTO;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.payload.request.QuizQuestionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizQuestionService {
    ResponseEntity<QuizQuestionDTO> createQuizQuestionAndAnswers( QuizQuestionRequest Quizrequest);
     List<QuizQuestion> getAllQuiz();
    List<QuizQuestion> GetByContent(String content);
    ResponseEntity<QuizQuestion> UpdateQuizQuestion(int quizquestionId, QuizQuestionRequest quizRequest);

    ResponseEntity<String> deleteQuizById(Long quizquestionId);
}

package com.quiztoast.backend_api.service.quiz;

import com.quiztoast.backend_api.model.dto.QuizQuestionDTO;
import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.payload.request.CreateQuizQuestionRequest;
import com.quiztoast.backend_api.model.payload.request.QuizQuestionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizQuestionService {
    ResponseEntity<QuizQuestion> createQuizQuestionAndAnswers( QuizQuestionDTO quizQuestionDTO);
     List<QuizQuestion> getAllQuizQuestions();

    List<QuizQuestionDTO> getAllQuizDTO();

    List<QuizQuestion> getByContent(String content);

    QuizQuestion createQuizQuestion(CreateQuizQuestionRequest quizQuestionRequest, Category category);
    ResponseEntity<QuizQuestion> updateQuizQuestion(int quizQuestionId, QuizQuestionRequest quizRequest);

    ResponseEntity<String> deleteQuizById(Long quizQuestionId);

    QuizQuestion getQuizQuestionById(Long quizQuestionId);
}

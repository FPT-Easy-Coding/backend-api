package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.payload.Request.QuizRequest;
import com.quizztoast.backendAPI.model.payload.Response.QuizQuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    List<QuizDTO> getAllQuiz();

    ResponseEntity<QuizDTO> createQuiz(QuizRequest quizRequest);

    ResponseEntity<String> deleteQuizById(int quiz_id);

    ResponseEntity<QuizDTO> UpdateQuiz(int quizId,QuizRequest quizRequest);

    List<QuizDTO> GetQuizByContent(String content);
    List<QuizQuestionResponse> getQuizQuestionsAndAnswersByQuizId(int quizId);

    ResponseEntity<?> increaseView(int quizId);
    ResponseEntity<?> upDateTimeQuiz(int quizId);
}

package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.payload.QuizRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    List<QuizDTO> getAllQuiz();

    ResponseEntity<QuizDTO> createQuiz(QuizRequest quizRequest);

    ResponseEntity<String> deleteQuizById(int quiz_id);

    ResponseEntity<QuizDTO> UpdateQuiz(int quizId,QuizRequest quizRequest);
}

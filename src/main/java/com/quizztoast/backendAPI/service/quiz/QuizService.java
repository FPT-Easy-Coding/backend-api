package com.quizztoast.backendAPI.service.quiz;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.DoQuiz;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.request.QuizRequest;
import com.quizztoast.backendAPI.model.payload.response.QuizQuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuizService {

    List<QuizDTO> getAllQuiz();

    ResponseEntity<QuizDTO> createQuiz(QuizRequest quizRequest);

    ResponseEntity<String> deleteQuizById(int quiz_id);

    ResponseEntity<QuizDTO> UpdateQuiz(int quizId,QuizRequest quizRequest);

    List<QuizDTO> GetQuizByContent(String content);
    ResponseEntity<?> getQuizQuestionsAndAnswersByQuizId(int quizId);

    ResponseEntity<?> increaseView(int quizId);
    ResponseEntity<?> upDateTimeQuiz(int quizId);

    ResponseEntity<?> getQuizByCategory(int categoryId);

    public List<DoQuiz> getLearnedQuizzesByUser(User user);
    ResponseEntity<?> GetQuizCreateByUser(Long userId);

    public int getNumberOfQuestionsByQuizId(int quizId);
}

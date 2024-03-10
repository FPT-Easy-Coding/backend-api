package com.quiztoast.backend_api.service.quiz;

import com.quiztoast.backend_api.model.dto.QuizDTO;
import com.quiztoast.backend_api.model.entity.quiz.DoQuiz;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.payload.request.CreateQuizRequest;
import com.quiztoast.backend_api.model.payload.request.QuizRequest;
import com.quiztoast.backend_api.model.payload.response.RateQuizResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizService {

    List<QuizDTO> getAllQuiz();

    ResponseEntity<QuizDTO> createQuiz(QuizRequest quizRequest);
    Quiz createQuizSet(CreateQuizRequest createQuizRequest);
    ResponseEntity<String> deleteQuizById(int quiz_id);

    ResponseEntity<QuizDTO> updateQuiz(int quizId, QuizRequest quizRequest);

    List<QuizDTO> getQuizByContent(String content);
    ResponseEntity<?> getQuizQuestionsAndAnswersByQuizId(int quizId);

    ResponseEntity<?> increaseView(int quizId);
    ResponseEntity<?> upDateTimeQuiz(int quizId);

    ResponseEntity<?> getQuizByCategory(int categoryId);

    public List<DoQuiz> getLearnedQuizzesByUser(User user);
    ResponseEntity<?> getQuizCreatedByUser(Long userId);

    public int getNumberOfQuestionsByQuizId(int quizId);

    Float getRateByQuiz(int quizId);

    ResponseEntity<RateQuizResponse> createRateQuiz(int quizId, long userId, float rate);

    ResponseEntity<RateQuizResponse> UpdateRateQuiz(int quizId, long userId, float rate);


    ResponseEntity<?> getUserRateQuiz(Long userId, int quizId);
}

package com.quiztoast.backend_api.service.quiz;

import com.quiztoast.backend_api.model.entity.quiz.QuizAnswer;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.payload.request.CreateQuizAnswerRequest;
import com.quiztoast.backend_api.model.payload.request.UpdateQuizAnswerRequest;
import com.quiztoast.backend_api.model.payload.request.UpdateQuizQuestionRequest;


public interface QuizAnswerService {
    void createQuizAnswer(CreateQuizAnswerRequest quizAnswer, QuizQuestion quizQuestion);
    void updateQuizAnswer(UpdateQuizAnswerRequest updateQuizAnswerRequest, QuizQuestion quizQuestion);
}

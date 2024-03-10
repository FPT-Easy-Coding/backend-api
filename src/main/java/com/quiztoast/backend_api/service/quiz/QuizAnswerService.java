package com.quiztoast.backend_api.service.quiz;

import com.quiztoast.backend_api.model.entity.quiz.QuizAnswer;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.payload.request.CreateQuizAnswerRequest;


public interface QuizAnswerService {
    void createQuizAnswer(CreateQuizAnswerRequest quizAnswer, QuizQuestion quizQuestion);
}

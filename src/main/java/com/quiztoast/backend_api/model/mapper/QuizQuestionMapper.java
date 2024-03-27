package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.dto.QuizAnswerDTO;
import com.quiztoast.backend_api.model.dto.QuizQuestionDTO;
import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.payload.request.CreateQuizQuestionRequest;
import com.quiztoast.backend_api.model.payload.request.QuizQuestionRequest;
import com.quiztoast.backend_api.model.payload.request.UpdateQuizQuestionRequest;
import com.quiztoast.backend_api.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static com.quiztoast.backend_api.model.mapper.QuizAnswerMapper.listQuizAnswerReqToDTO;

public class QuizQuestionMapper {


    public static QuizQuestion mapCreateRequestToQuizQuestion(CreateQuizQuestionRequest quizQuestionRequest, Category category) {
        return QuizQuestion.builder()
                .content(quizQuestionRequest.getQuestion())
                .createdAt(LocalDateTime.now())
                .categoryId(category)
                .build();
    }

    public static QuizQuestion mapUpdateRequestToQuizQuestion(QuizQuestion quizQuestion,UpdateQuizQuestionRequest quizQuestionRequest, Category category) {
        return QuizQuestion.builder()
                .quizQuestionId(quizQuestion.getQuizQuestionId())
                .content(quizQuestionRequest.getQuestion())
                .createdAt(quizQuestion.getCreatedAt())
                .categoryId(category)
                .build();
    }
}

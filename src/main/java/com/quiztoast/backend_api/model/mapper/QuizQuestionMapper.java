package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.dto.QuizAnswerDTO;
import com.quiztoast.backend_api.model.dto.QuizQuestionDTO;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.payload.request.CreateQuizQuestionRequest;
import com.quiztoast.backend_api.model.payload.request.QuizQuestionRequest;
import com.quiztoast.backend_api.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static com.quiztoast.backend_api.model.mapper.QuizAnswerMapper.listQuizAnswerReqToDTO;

public class QuizQuestionMapper {
    private final CategoryRepository categoryRepository;

    public QuizQuestionMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static QuizQuestionDTO mapQuizQuesRequestToDTO(QuizQuestionRequest quiz) {
        List<QuizAnswerDTO> listAnswer = listQuizAnswerReqToDTO(quiz.getAnswers());
        return QuizQuestionDTO.builder()
                .categoryId(quiz.getCategoryId())
                .questionContent(quiz.getQuestionContent())
//                .answers(listAnswer)
                .build();
    }

    public static QuizQuestion mapQuizQuesRequestToQuizQuestion(QuizQuestionRequest quiz) {
        return QuizQuestion.builder()
                .content(quiz.getQuestionContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static QuizQuestion mapCreateRequestToQuizQuestion(CreateQuizQuestionRequest quizRequest) {
        return QuizQuestion.builder()
                .content(quizRequest.getQuestion())
                .createdAt(LocalDateTime.now())
                .build();
    }
}

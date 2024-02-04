package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizQuestionDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.payload.Request.QuizQuestionRequest;
import com.quizztoast.backendAPI.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static com.quizztoast.backendAPI.model.mapper.QuizAnswerMapper.ListQuizAnswerReqToDTO;

public class QuizQuestionMapper {
    private final CategoryRepository categoryRepository;

    public QuizQuestionMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static QuizQuestionDTO mapQuizQuesRequestToDTO(QuizQuestionRequest quiz) {
        List<QuizAnswerDTO> listAnswer = ListQuizAnswerReqToDTO(quiz.getAnswers());
        return QuizQuestionDTO.builder()
                .categoryId(quiz.getCategoryId())
                .questionContent(quiz.getQuestionContent())
                .answers(listAnswer)
                .build();
    }

    public static QuizQuestion mapQuizQuesRequestToQuizQuestion(QuizQuestionRequest quiz) {
        return QuizQuestion.builder()
                .content(quiz.getQuestionContent())
                .createdAt(LocalDateTime.now())
                .build();
    }
}

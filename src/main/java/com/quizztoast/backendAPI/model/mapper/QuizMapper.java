package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.payload.response.QuizSetResponse;
import com.quizztoast.backendAPI.repository.RateQuizRepository;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {

    public static QuizDTO mapQuizDTOToUser(Quiz quiz, int numberOfQuestion, RateQuizRepository rateQuizRepository) {
        return QuizDTO.builder()
                .userFirstName(quiz.getUser().getFirstName())
                .userLastName(quiz.getUser().getLastName())
                .userName(quiz.getUser().getUserName())
                .userId(quiz.getUser().getUserId())
                .quizId(quiz.getQuizId())
                .quizName(quiz.getQuizName())
                .categoryId(quiz.getCategory().getCategoryId())
//                .rate(rateQuizRepository.averageRateOfQuiz(quiz.getQuizId()))
                .view(quiz.getViewOfQuiz())
                .numberOfQuestions(numberOfQuestion)
                .createAt(quiz.getCreatedAt())
                .timeRecentViewQuiz(quiz.getTimeRecentViewQuiz())
                .build();
    }

    public static QuizSetResponse mapQuizToQuizSetResponse(Quiz quiz,int numberOfQuestion){
        return QuizSetResponse.builder()
                .quizId(quiz.getQuizId())
                .quizName(quiz.getQuizName())
                .authorFirstName(quiz.getUser().getFirstName())
                .authorLastName(quiz.getUser().getLastName())
                .author(quiz.getUser().getUserName())
                .createdAt(quiz.getCreatedAt())
                .numberOfQuestion(numberOfQuestion)
                .build();
    }
}

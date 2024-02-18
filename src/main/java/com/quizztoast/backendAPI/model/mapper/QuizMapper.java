package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {

    public static QuizDTO mapQuizDTOToUser(Quiz quiz){
        return QuizDTO.builder()
                .userFirstName(quiz.getUser().getFirstName())
                .userLastName(quiz.getUser().getLastName())
                .userName(quiz.getUser().getUserName())
                .userId(quiz.getUser().getUserId())
                .quizId(quiz.getQuizId())
                .quizName(quiz.getQuizName())
                .categoryId(quiz.getCategory().getCategoryId())
                .rate(quiz.getRate())
                .view(quiz.getViewOfQuiz())
                .numberOfQuestions(quiz.getNumberOfQuizQuestion())
                .createAt(quiz.getCreatedAt())
                .timeRecentViewQuiz(quiz.getTimeRecentViewQuiz())
                .build();
    }
    public static List<QuizDTO> quizToQuizDTO(List<Quiz> quiz)
    {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quizSet : quiz) {
            quizDTOList.add(mapQuizDTOToUser(quizSet));
        }
        return quizDTOList;
    }
}

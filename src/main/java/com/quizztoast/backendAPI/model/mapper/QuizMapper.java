package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.payload.response.QuizSetResponse;
import com.quizztoast.backendAPI.repository.QuizQuestionMappingRepository;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {

    public static QuizDTO mapQuizDTOToUser(Quiz quiz, QuizQuestionMappingRepository quizQuestionMappingRepository){
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
                .numberOfQuestions(quizQuestionMappingRepository.findQuizByQuizID(quiz.getQuizId()))
                .createAt(quiz.getCreatedAt())
                .timeRecentViewQuiz(quiz.getTimeRecentViewQuiz())
                .build();
    }
    public static List<QuizDTO> quizToQuizDTO(List<Quiz> quiz,QuizQuestionMappingRepository quizQuestionMappingRepository)
    {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quizSet : quiz) {
            quizDTOList.add(mapQuizDTOToUser(quizSet,quizQuestionMappingRepository));
        }
        return quizDTOList;
    }

    public static QuizSetResponse mapQuizToQuizSetResponse(Quiz quiz,QuizQuestionMappingRepository quizQuestionMappingRepository){
        return QuizSetResponse.builder()
                .quizId(quiz.getQuizId())
                .quizName(quiz.getQuizName())
                .authorFirstName(quiz.getUser().getFirstName())
                .authorLastName(quiz.getUser().getLastName())
                .author(quiz.getUser().getUserName())
                .numberOfQuestion(quizQuestionMappingRepository.findQuizByQuizID(quiz.getQuizId()))
                .build();
    }
}

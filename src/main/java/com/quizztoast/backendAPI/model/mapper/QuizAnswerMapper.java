package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.payload.Request.QuizAnswerRequest;

import java.util.ArrayList;
import java.util.List;

public class QuizAnswerMapper {
    public static QuizAnswerDTO MapQuizAnswerRequestToDTO(QuizAnswerRequest quiz){
        return QuizAnswerDTO.builder()
                .content(quiz.getContent())
                .isCorrect(quiz.isCorrect())
                .build();
    }
    public static List<QuizAnswerDTO> ListQuizAnswerReqToDTO(List<QuizAnswerRequest> quiz)
    {
        List<QuizAnswerDTO> quizDTOList = new ArrayList<>();
        for (QuizAnswerRequest quizset : quiz) {
            quizDTOList.add(MapQuizAnswerRequestToDTO(quizset));
        }
        return quizDTOList;
    }

}

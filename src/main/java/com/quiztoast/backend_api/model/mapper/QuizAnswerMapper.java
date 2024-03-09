package com.quiztoast.backend_api.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.entity.quiz.QuizAnswer;
import com.quizztoast.backendAPI.model.payload.request.QuizAnswerRequest;

import java.util.ArrayList;
import java.util.List;

public class QuizAnswerMapper {
    public static QuizAnswerDTO MapQuizAnswerRequestToDTO(QuizAnswerRequest quiz){
        return QuizAnswerDTO.builder()
                .content(quiz.getContent())
                .isIsCorrect(quiz.isCorrect())
                .build();
    }
    public static List<QuizAnswerDTO> ListQuizAnswerReqToDTO(List<QuizAnswerRequest> quiz)
    {
        List<QuizAnswerDTO> quizDTOList = new ArrayList<>();
        for (QuizAnswerRequest quizSet : quiz) {
            quizDTOList.add(MapQuizAnswerRequestToDTO(quizSet));
        }
        return quizDTOList;
    }

    public static List<QuizAnswerDTO> MapQuizAnswerEntityToDTO(List<QuizAnswer> quiz){
        List<QuizAnswerDTO> quizDTOList = new ArrayList<>();
        for (QuizAnswer quizSet : quiz) {
            quizDTOList.add(QuizAnswerDTO.builder()
                    .answerId(quizSet.getQuizAnswerId())
                    .content(quizSet.getContent())
                    .isIsCorrect(quizSet.getIsCorrect())
                    .build());
        }
        return quizDTOList;
    }

}

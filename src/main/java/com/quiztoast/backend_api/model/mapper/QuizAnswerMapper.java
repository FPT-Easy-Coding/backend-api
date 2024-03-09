package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.dto.QuizAnswerDTO;
import com.quiztoast.backend_api.model.payload.request.QuizAnswerRequest;

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

}

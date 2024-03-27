package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.dto.QuizAnswerDTO;
import com.quiztoast.backend_api.model.entity.quiz.QuizAnswer;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.payload.request.CreateQuizAnswerRequest;
import com.quiztoast.backend_api.model.payload.request.QuizAnswerRequest;
import com.quiztoast.backend_api.model.payload.request.UpdateQuizAnswerRequest;
import com.quiztoast.backend_api.model.payload.request.UpdateQuizQuestionRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuizAnswerMapper {
    public static QuizAnswerDTO mapQuizAnswerRequestToDTO(QuizAnswerRequest quiz){
        return QuizAnswerDTO.builder()
                .content(quiz.getContent())
                .isIsCorrect(quiz.isCorrect())
                .build();
    }
    public static List<QuizAnswerDTO> listQuizAnswerReqToDTO(List<QuizAnswerRequest> quiz)
    {
        List<QuizAnswerDTO> quizDTOList = new ArrayList<>();
        for (QuizAnswerRequest quizSet : quiz) {
            quizDTOList.add(mapQuizAnswerRequestToDTO(quizSet));
        }
        return quizDTOList;
    }

    public static List<QuizAnswerDTO> mapQuizAnswerEntityToDTO(List<QuizAnswer> quiz){
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

    public static QuizAnswer mapCreateRequestToQuizAnswer(CreateQuizAnswerRequest quizRequest, QuizQuestion quizQuestion) {
        return QuizAnswer.builder()
                .quizQuestion(quizQuestion)
                .content(quizRequest.getContent())
                .isCorrect(quizRequest.getIsCorrect())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static QuizAnswer mapUpdateRequestToQuizAnswer(QuizAnswer quizAnswer,UpdateQuizAnswerRequest updateQuizQuestionRequest, QuizQuestion quizQuestion) {
        return QuizAnswer.builder()
                .quizAnswerId(quizAnswer.getQuizAnswerId())
                .quizQuestion(quizQuestion)
                .content(updateQuizQuestionRequest.getContent())
                .isCorrect(updateQuizQuestionRequest.getIsCorrect())
                .createdAt(quizAnswer.getCreatedAt())
                .build();
    }
}

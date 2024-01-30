package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizQuestionDTO;
import com.quizztoast.backendAPI.model.dto.UserDTO;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.Request.QuizAnswerRequest;
import com.quizztoast.backendAPI.model.payload.Request.QuizQuestionRequest;

import java.util.ArrayList;
import java.util.List;

import static com.quizztoast.backendAPI.model.mapper.QuizAnswerMapper.ListQuizAnswerReqToDTO;

public class QuizQuestionMapper {
    public static QuizQuestionDTO MapQuizQuesRequestToDTO(QuizQuestionRequest quiz){
        List<QuizAnswerDTO> listAnswer = ListQuizAnswerReqToDTO(quiz.getAnswers());
        return QuizQuestionDTO.builder()
                .categoryId(quiz.getCategoryId())
                .questionContent(quiz.getQuestionContent())
                .answers(listAnswer)
                .build();
    }



}

package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.dto.UserDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class QuizMapper {

    public static QuizDTO mapQuizDTOToUser(Quiz quiz){
        return QuizDTO.builder()
                .user_first_name(quiz.getUser().getFirstName())
                .user_last_name(quiz.getUser().getLastName())
                .user_id(quiz.getUser().getUserId())
                .quiz_name(quiz.getQuiz_name())
                .category_id(quiz.getCategory().getCategory_id())
                .rate(quiz.getRate())
                .number_of_questions(quiz.getQuiz_ques_id())
                .create_at(quiz.getCreated_at())
                .build();
    }
    public static List<QuizDTO> quizToQuizDTO(List<Quiz> quiz)
    {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quizset : quiz) {
            quizDTOList.add(mapQuizDTOToUser(quizset));
        }
        return quizDTOList;
    }
}

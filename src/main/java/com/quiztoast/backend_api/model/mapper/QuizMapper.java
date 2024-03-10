package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.dto.QuizDTO;
import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.payload.request.CreateQuizRequest;
import com.quiztoast.backend_api.model.payload.response.QuizSetResponse;
import com.quiztoast.backend_api.repository.RateQuizRepository;

import java.time.LocalDateTime;

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

    public static Quiz mapCreateRequestToQuiz(CreateQuizRequest createQuizRequest, User user, Category category) {
        return Quiz.builder()
                .user(user)
                .quizName(createQuizRequest.getTitle())
                .category(category)
                .rate(0)
                .viewOfQuiz(0L)
                .createdAt(LocalDateTime.now())
                .timeRecentViewQuiz(LocalDateTime.now())
                .build();
    }
}

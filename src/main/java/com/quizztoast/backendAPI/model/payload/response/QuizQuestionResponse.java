package com.quizztoast.backendAPI.model.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizQuestionResponse {
    private Long userId;
    private int quizId;
    private String userName;
    private String userFirstName;
    private String userLastName;
    private int categoryId;
    private String categoryName;
    private String quizName;
    private double rate;
    private int numberOfQuestions;
    private LocalDateTime createAt;
    private Long view;
    private LocalDateTime timeRecentViewQuiz;

    private List<QuestionData> questions;
}



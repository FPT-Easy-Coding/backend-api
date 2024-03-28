package com.quiztoast.backend_api.model.payload.response;

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
    private String avatar;
    private int categoryId;
    private String categoryName;
    private String quizName;
    private double rate;
    private int numberOfQuestions;
    private String description;
    private LocalDateTime createAt;
    private Long view;
    private LocalDateTime timeRecentViewQuiz;
    private List<QuestionData> questions;
}



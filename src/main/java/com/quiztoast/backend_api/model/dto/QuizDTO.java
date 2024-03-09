package com.quiztoast.backend_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDTO {
    private Long userId;
    private int quizId;
    private String userName;
    private String userFirstName;
    private String userLastName;
    private int categoryId;
    private String quizName;
    private double rate;
    private int numberOfQuestions;
    private LocalDateTime createAt;
    private Long view;
    private LocalDateTime timeRecentViewQuiz;
}

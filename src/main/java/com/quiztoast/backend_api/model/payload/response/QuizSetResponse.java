package com.quiztoast.backend_api.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizSetResponse {
    private int quizId;
    private String quizName;
    private String author;
    private String authorFirstName;
    private String authorLastName;
    private int numberOfQuestion;
    private LocalDateTime createdAt;
}

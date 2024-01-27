package com.quizztoast.backendAPI.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizAnswerDTO {

    private String content;
    private boolean isCorrect;
}

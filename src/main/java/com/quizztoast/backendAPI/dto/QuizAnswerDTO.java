package com.quizztoast.backendAPI.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizAnswerDTO {
    @Valid
    @NotNull(message = "questionContent cannot be null")
    @NotEmpty(message = "questionContent cannot be blank")
    private String content;
    @NotNull
    private Boolean isCorrect;
}

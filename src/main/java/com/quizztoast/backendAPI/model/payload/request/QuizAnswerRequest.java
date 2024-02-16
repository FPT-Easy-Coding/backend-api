package com.quizztoast.backendAPI.model.payload.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerRequest {
    @Valid
    @NotNull(message = "categoryId cannot be null")
    @NotBlank(message = "categoryId cannot be Blank")
    private String content;
    @NotNull(message = "isCorrect cannot be null")
    private boolean isCorrect;
}

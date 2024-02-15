package com.quizztoast.backendAPI.model.payload.Request;

import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {

    @Valid
    @NotNull(message = "userId cannot be null")
    private Long userId;
    @NotNull(message = "categoryId cannot be null")
    private Integer categoryId;
    @NotBlank(message = "quizName cannot be blank")
    private String quizName;
    private List<QuizQuestionRequest> listQuestion;
    private Double rate;
    private LocalDateTime createAt;

}
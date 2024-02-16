package com.quizztoast.backendAPI.model.payload.request;

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
    @NotNull(message = "user_id cannot be null")
    private Long user_id;
    @NotNull(message = "category_id cannot be null")
    private Integer category_id;
    @NotNull(message = "quiz_name cannot be null")
    @NotBlank(message = "quiz_name cannot be blank")
    private String quiz_name;
    private List<QuizQuestionRequest> list_question;
    private Double rate;
    private LocalDateTime create_at;

}
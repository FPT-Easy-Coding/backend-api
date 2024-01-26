package com.quizztoast.backendAPI.dto;

import com.quizztoast.backendAPI.model.quiz.QuizQuestionMapping;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuizDTO {
    @NotNull(message = "quiz_id cannot be null")
    @NotBlank(message = "quiz_id cannot be blank")
    private int quiz_id;
    private Long user_id;
    private int class_id;
    @NotNull(message = "category_id cannot be null")
    @NotBlank(message = "category_id cannot be blank")
    private int category_id;
    @NotNull(message = "quiz_name cannot be null")
    @NotBlank(message = "quiz_name cannot be blank")
    private String quiz_name;
    private int rate;
    private LocalDateTime create_at;
    private List<Long> quiz_ques_ids;
}

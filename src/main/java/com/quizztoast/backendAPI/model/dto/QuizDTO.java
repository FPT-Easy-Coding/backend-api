package com.quizztoast.backendAPI.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuizDTO {

    @Valid
    @NotNull(message = "quiz_id cannot be null")
    private Integer quiz_id;

    private Long user_id;
    private Integer class_id;

    @NotNull(message = "category_id cannot be null")
    private Integer category_id;

    @NotNull(message = "quiz_name cannot be null")
    private String quiz_name;

    private Double rate;
    private LocalDateTime create_at;
    private List<Long> quiz_ques_ids;

    // Getters and setters
}

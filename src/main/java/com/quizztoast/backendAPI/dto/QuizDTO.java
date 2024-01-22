package com.quizztoast.backendAPI.dto;

import com.quizztoast.backendAPI.model.quiz.QuizQuestionMapping;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuizDTO {
    private List<QuizQuestionMapping> quiz_id;
    private int class_id;
    private int category_id;
    private String quiz_name;
    private int rate;
    private LocalDateTime create_at;
}

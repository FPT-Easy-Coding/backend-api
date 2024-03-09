package com.quiztoast.backend_api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDTO {
    private String categoryName;
    private LocalDateTime createAt;
    private int totalQuiz;
}

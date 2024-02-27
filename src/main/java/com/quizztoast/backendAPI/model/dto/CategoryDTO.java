package com.quizztoast.backendAPI.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDTO {
    private String categoryName;
    private LocalDateTime createAt;
    private int totalQuiz;
}

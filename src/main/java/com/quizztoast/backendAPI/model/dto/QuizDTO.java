package com.quizztoast.backendAPI.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuizDTO {
    private Long user_id;
    private int category_id;
    private String quiz_name;
    private double rate;
    private LocalDateTime create_at;
}

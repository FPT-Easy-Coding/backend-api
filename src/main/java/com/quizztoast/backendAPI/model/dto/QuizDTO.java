package com.quizztoast.backendAPI.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuizDTO {
    private Long user_id;
    private String user_first_name;
    private String user_last_name;
    private int category_id;
    private String quiz_name;
    private double rate;
    private int number_of_questions;
    private LocalDateTime create_at;
}

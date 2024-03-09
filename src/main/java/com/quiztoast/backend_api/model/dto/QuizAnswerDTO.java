package com.quiztoast.backend_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizAnswerDTO {
    private long answerId;
    private String content;
    private boolean isIsCorrect;

}

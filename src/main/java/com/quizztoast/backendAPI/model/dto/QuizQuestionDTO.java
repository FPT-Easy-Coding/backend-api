package com.quizztoast.backendAPI.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizQuestionDTO {
    private int categoryId;
    private String questionContent;
    private List<QuizAnswerDTO> answers;
    private Boolean isCorrect;
}

package com.quizztoast.backendAPI.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizCreationRequestDTO {
    private int categoryId;
    private String questionContent;
    private List<QuizAnswerDTO> answers;
}

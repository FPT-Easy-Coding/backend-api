package com.quizztoast.backendAPI.model.dto;

import com.quizztoast.backendAPI.model.entity.quiz.QuizAnswer;
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
    private long questionId;
    private int categoryId;
    private String categoryName;
    private String questionContent;
    private List<QuizAnswerDTO> answers;
    private List<QuizAnswer> answersEntity;
    private Boolean isCorrect;
}

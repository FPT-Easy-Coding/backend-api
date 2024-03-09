package com.quiztoast.backend_api.model.dto;

import com.quiztoast.backend_api.model.entity.quiz.QuizAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
//    private List<QuizAnswerDTO> answers;
    private List<QuizAnswerDTO> answersEntity;
    private Boolean isCorrect;
}

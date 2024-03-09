package com.quizztoast.backendAPI.model.payload.request;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionRequest {
    @Valid
//    @NotNull(message = "categoryId cannot be null")
    private int categoryId;
    private String categoryName;
//    @NotNull(message = "questionContent cannot be null")
//    @NotEmpty(message = "questionContent cannot be blank")
    private String questionContent;
//    @NotNull(message = "answers cannot be null")
//    @NotEmpty(message = "answers cannot be blank")
    private List<QuizAnswerRequest> answers;
    private List<QuizAnswerDTO> answersEntity;
//    @NotNull(message = "isCorrect cannot be null")
    private Boolean isCorrect;
}

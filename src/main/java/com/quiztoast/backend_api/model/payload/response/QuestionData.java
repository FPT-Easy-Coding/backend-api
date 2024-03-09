package com.quiztoast.backend_api.model.payload.response;

import com.quiztoast.backend_api.model.dto.QuizAnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionData {
    private String questionContent;
    private List<QuizAnswerDTO> answers;
}

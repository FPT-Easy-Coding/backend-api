package com.quizztoast.backendAPI.model.payload.response;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizQuestionResponse {
        private String questionContent;
        private List<QuizAnswerDTO> answers;
    }


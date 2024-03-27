package com.quiztoast.backend_api.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UpdateQuizQuestionRequest {
    private long quizQuestionId;
    private String question;
    private List<UpdateQuizAnswerRequest> answers;
}

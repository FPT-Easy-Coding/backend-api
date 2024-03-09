package com.quiztoast.backend_api.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AnswerRequest {
    private String content;
    private int questionId;
    private Long userId;
}

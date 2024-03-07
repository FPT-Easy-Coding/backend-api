package com.quizztoast.backendAPI.service.classroom;

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

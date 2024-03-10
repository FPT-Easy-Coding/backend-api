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
public class CreateQuizRequest {
    private int categoryId = 1;
    private Long userId;
    private String title;
    private String description;
    private List<CreateQuizQuestionRequest> questions;
}

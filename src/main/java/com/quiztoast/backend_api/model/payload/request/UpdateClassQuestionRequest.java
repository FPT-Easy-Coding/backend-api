package com.quiztoast.backend_api.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UpdateClassQuestionRequest {
    private int classroomId;
    private int questionId;
    private String title;
    private String content;
}

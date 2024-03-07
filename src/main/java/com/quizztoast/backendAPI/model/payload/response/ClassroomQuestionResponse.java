package com.quizztoast.backendAPI.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ClassroomQuestionResponse {
    private int classQuestionId;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private boolean isAnswered;
    private String userName;
    private String userFirstName;
    private String userLastName;
}

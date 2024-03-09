package com.quiztoast.backend_api.model.payload.response;

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
    private ClassroomAnswerResponse classroomAnswerResponse;
    private LocalDateTime createAt;
    private boolean isAnswered;
    private String userName;
    private String userFirstName;
    private String userLastName;

    // Method to get the appropriate text based on isAnswered
    public String getContentOrAnswer() {
        if (isAnswered) {
            if (classroomAnswerResponse != null && classroomAnswerResponse.getContent() != null) {
                return classroomAnswerResponse.getContent();
            }
            return null;
        }
        return content;
    }
}

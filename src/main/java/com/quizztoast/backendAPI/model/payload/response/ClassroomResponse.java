package com.quizztoast.backendAPI.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomResponse {
    private String classroomName;
    private Long numberOfStudent;
    private Long numberOfQuizSet;
    private String slugCode;
}

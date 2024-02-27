package com.quizztoast.backendAPI.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomToProfileResponse {
    private int classId;
    private String className;
    private Long numberOfStudent;
    private Long numberOfQuizSet;
    private String teacherName;
}

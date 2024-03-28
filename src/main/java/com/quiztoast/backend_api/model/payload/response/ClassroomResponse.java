package com.quiztoast.backend_api.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomResponse {
    private int classId;
    private String className;
    private String teacherName;
    private Long teacherId;
    private Long numberOfStudent;
    private Long numberOfQuizSet;
    private String slugCode;
}

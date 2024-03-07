package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.entity.classroom.ClassroomAnswer;
import com.quizztoast.backendAPI.model.payload.response.ClassroomAnswerResponse;

public class ClassroomAnswerMapper {
    public static ClassroomAnswerResponse mapClassroomAnswerToClassroomAnswerResponse(ClassroomAnswer classroomAnswer) {
        return ClassroomAnswerResponse.builder()
                .content(classroomAnswer.getContent())
                .userId(classroomAnswer.getUser().getUserId())
                .build();
    }
}

package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.entity.classroom.ClassroomAnswer;
import com.quiztoast.backend_api.model.payload.response.ClassroomAnswerResponse;

public class ClassroomAnswerMapper {
    public static ClassroomAnswerResponse mapClassroomAnswerToClassroomAnswerResponse(ClassroomAnswer classroomAnswer) {
        return ClassroomAnswerResponse.builder()
                .content(classroomAnswer.getContent())
                .userId(classroomAnswer.getUser().getUserId())
                .build();
    }
}

package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.payload.response.ClassroomResponse;

public class ClassroomMapper {

    public static ClassroomResponse mapClassroomToClassroomResponse(Classroom classroom, Long numberOfStudents, Long numberOfQuizSets) {
        String className = classroom.getClassName();
        return ClassroomResponse.builder()
                .className(className)
                .numberOfStudent(numberOfStudents)
                .numberOfQuizSet(numberOfQuizSets)
                .build();
    }
}

package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.ClassroomDTO;
import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.request.ClassroomRequest;
import com.quizztoast.backendAPI.model.payload.response.ClassroomResponse;
import com.quizztoast.backendAPI.model.payload.response.ClassroomToProfileResponse;
import com.quizztoast.backendAPI.util.ClassroomSlugGenerator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@RequiredArgsConstructor
public class ClassroomMapper {
    public static ClassroomToProfileResponse mapClassroomToClassroomCardResponse(Classroom classroom, Long numberOfStudents, Long numberOfQuizSets) {
        String className = classroom.getClassroomName();
        User user = classroom.getUser();
        return ClassroomToProfileResponse.builder()
                .className(className)
                .teacherName(user.getUserName())
                .numberOfStudent(numberOfStudents)
                .numberOfQuizSet(numberOfQuizSets)
                .build();
    }
    public static ClassroomResponse mapClassroomToClassroomResponse(Classroom classroom, Long numberOfStudents, Long numberOfQuizSets) {
        String className = classroom.getClassroomName();
        return ClassroomResponse.builder()
                .classroomName(className)
                .numberOfStudent(numberOfStudents)
                .numberOfQuizSet(numberOfQuizSets)
                .slugCode(classroom.getSlugCode())
                .build();
    }

    public static Classroom mapClassroomRequestToClassroom(ClassroomRequest classroomRequest, User user) {
        return Classroom.builder()
                .classroomName(classroomRequest.getClassroomName())
                .createdAt(LocalDateTime.now())
                .user(user)
                .slugCode(ClassroomSlugGenerator.generateSlug())
                .build();
    }

    public static ClassroomDTO mapClassroomToClassroomDto(Classroom classroom) {
        return ClassroomDTO.builder()
                .classroomName(classroom.getClassroomName())
                .build();
    }
}

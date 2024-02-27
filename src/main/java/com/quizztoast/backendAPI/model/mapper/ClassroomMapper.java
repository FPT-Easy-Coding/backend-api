package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.ClassroomDTO;
import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.UserBelongClassroom;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.request.ClassroomRequest;
import com.quizztoast.backendAPI.model.payload.response.ClassMemberResponse;
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
                .classId(classroom.getClassroomId())
                .className(className)
                .teacherName(user.getUserName())
                .numberOfStudent(numberOfStudents)
                .numberOfQuizSet(numberOfQuizSets)
                .build();
    }
    public static ClassroomResponse mapClassroomToClassroomResponse(Classroom classroom, Long numberOfStudents, Long numberOfQuizSets) {
        String className = classroom.getClassroomName();
        return ClassroomResponse.builder()
                .classId(classroom.getClassroomId())
                .className(className)
                .teacherName(classroom.getUser().getUserName())
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

    public static ClassMemberResponse mapUserBelongClassroomToClassMemberResponse(UserBelongClassroom userBelongClassroom) {
        return ClassMemberResponse.builder()
                .userName(userBelongClassroom.getId().getUser().getUserName())
                .userFirstName(userBelongClassroom.getId().getUser().getFirstName())
                .userLastName(userBelongClassroom.getId().getUser().getLastName())
                .build();
    }
}

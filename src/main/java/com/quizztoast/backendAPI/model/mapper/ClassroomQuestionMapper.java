package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.entity.classroom.ClassroomAnswer;
import com.quizztoast.backendAPI.model.entity.classroom.ClassroomQuestion;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.response.ClassroomAnswerResponse;
import com.quizztoast.backendAPI.model.payload.response.ClassroomQuestionResponse;

public class ClassroomQuestionMapper {
    public static ClassroomQuestionResponse mapClassroomQuestionToClassroomQuestionResponse(
            ClassroomQuestion classroomQuestion,
            User user,
            ClassroomAnswer classroomAnswer
    ) {
        return ClassroomQuestionResponse.builder()
                .classQuestionId(classroomQuestion.getClassQuestionId())
                .title(classroomQuestion.getTitle())
                .content(classroomQuestion.getContent())
                .createAt(classroomQuestion.getCreateAt())
                .isAnswered(classroomQuestion.isAnswered())
                .userName(user.getUserName())
                .userFirstName(user.getFirstName())
                .userLastName(user.getLastName())
                .classroomAnswerResponse(
                        classroomAnswer != null ? ClassroomAnswerMapper.mapClassroomAnswerToClassroomAnswerResponse(classroomAnswer) : null
                )
                .build();
    }
}

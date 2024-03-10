package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.entity.classroom.ClassroomAnswer;
import com.quiztoast.backend_api.model.entity.classroom.ClassroomQuestion;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.payload.response.ClassroomQuestionResponse;
import lombok.RequiredArgsConstructor;

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

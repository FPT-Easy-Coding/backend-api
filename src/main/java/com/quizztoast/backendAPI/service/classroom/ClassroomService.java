package com.quizztoast.backendAPI.service.classroom;


import com.quizztoast.backendAPI.model.dto.ClassroomDTO;
import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.QuizBelongClassroom;
import com.quizztoast.backendAPI.model.entity.classroom.UserBelongClassroom;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.request.ClassroomRequest;
import com.quizztoast.backendAPI.model.payload.response.ClassMemberResponse;
import com.quizztoast.backendAPI.model.payload.response.ClassroomToProfileResponse;

import java.util.List;

public interface ClassroomService {
    public List<Classroom> getAllClassroom();
    public Classroom findClassroomById(int classroomId);
    public List<Classroom> getListClassroomByUserId(int userId);
    public List<UserBelongClassroom> getListClassroomByUser(User user);
    public Long countClassroomByUser(User user);
    public Long countUserByClassroom(Classroom classroom);
    public Long countQuizByClassroom(Classroom classroom);
    public List<QuizBelongClassroom> getListQuizByClassroom(Classroom classroom);
    public Classroom createClassroom(ClassroomRequest classroomRequest);
    ClassroomToProfileResponse getClassroomCardDetails(Classroom classroom);
    public void deleteClassroom(Classroom classroom);
    public void deleteQuizFromClassroom(int classroomId, int quizId);
    public ClassroomDTO updateClassroom(int classroomId, ClassroomRequest classroomRequest);
    public void addQuestionToClassroom(int classroomId, int questionId);
    public void removeQuestionFromClassroom(int classroomId, int questionId);
    public void updateQuestionInClassroom(int classroomId, int questionId, Classroom classroom);
    public void addUserToClassroom(int classroomId, Long userId);
    public void removeUserFromClassroom(int classroomId, int userId);
    public void updateClassroomUser(int classroomId, int userId, Classroom classroom);
    public List<Classroom> findClassroomByTeacherId(int teacherId);

    public List<ClassMemberResponse> getClassMembers(int classroomId);

    void addQuizToClassroom(int classroomId, int quizId);
}

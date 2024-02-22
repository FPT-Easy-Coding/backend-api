package com.quizztoast.backendAPI.service.classroom;


import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.QuizBelongClassroom;
import com.quizztoast.backendAPI.model.entity.classroom.UserBelongClassroom;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.response.ClassroomResponse;

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
    public Classroom createClassroom(Classroom classroom);

    ClassroomResponse getClassroomDetails(Classroom classroom);
//    public void deleteClassroom(int classroomId);
//    public void updateClassroom(int classroomId, Classroom classroom);
//    public void addQuestionToClassroom(int classroomId, int questionId);
//    public void removeQuestionFromClassroom(int classroomId, int questionId);
//    public void updateQuestionInClassroom(int classroomId, int questionId, Classroom classroom);
//    public void addUserToClassroom(int classroomId, int userId);
//    public void removeUserFromClassroom(int classroomId, int userId);
//    public void updateClassroomUser(int classroomId, int userId, Classroom classroom);
//    public List<Classroom> findClassroomByTeacherId(int teacherId);
}

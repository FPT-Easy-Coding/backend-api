package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.QuizBelongClassroom;
import com.quizztoast.backendAPI.model.entity.classroom.QuizBelongClassroom.QuizBeLongClassroomId;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizBelongClassroomRepository  extends JpaRepository<QuizBelongClassroom, QuizBeLongClassroomId> {
    Long countByIdClassroom(Classroom classroom);
    List<QuizBelongClassroom> findByIdClassroom(Classroom classroom);
}

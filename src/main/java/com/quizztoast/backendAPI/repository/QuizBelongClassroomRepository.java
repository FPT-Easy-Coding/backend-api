package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.QuizBelongClassroom;
import com.quizztoast.backendAPI.model.entity.classroom.QuizBelongClassroom.QuizBeLongClassroomId;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizBelongClassroomRepository extends JpaRepository<QuizBelongClassroom, QuizBeLongClassroomId> {
    Long countByIdClassroom(Classroom classroom);

    List<QuizBelongClassroom> findByIdClassroom(Classroom classroom);

    @Modifying
    @Query("DELETE FROM QuizBelongClassroom q WHERE q.id.classroom = :classroom")
    void deleteQuizSetsByClassroom(@Param("classroom") Classroom classroom);

    @Modifying
    @Query("DELETE FROM QuizBelongClassroom qc WHERE qc.id.classroom.classroomId = :classroomId AND qc.id.quiz.quizId = :quizId")
    void deleteQuizFromClassroom(@Param("classroomId") int classroomId, @Param("quizId") int quizId);

    @Query("SELECT q FROM QuizBelongClassroom q WHERE q.id.classroom.classroomId = :classroomId AND q.id.quiz.quizId = :quizId")
    QuizBelongClassroom findByIdClassroomAndQuiz(int classroomId, int quizId);
}

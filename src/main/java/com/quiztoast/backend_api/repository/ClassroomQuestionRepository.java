package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.ClassroomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomQuestionRepository extends JpaRepository<ClassroomQuestion, Integer> {
    @Modifying
    @Query(value = "SELECT cq FROM ClassroomQuestion cq WHERE cq.classroom.classroomId = :classroomId")
    List<ClassroomQuestion> findByClassroomId(int classroomId);

    @Query(value = "SELECT cq FROM ClassroomQuestion cq WHERE cq.classQuestionId = :questionId")
    ClassroomQuestion findByQuestionId(int questionId);


    @Query(value = "DELETE FROM ClassroomQuestion cq WHERE cq.classQuestionId = :questionId AND cq.classroom.classroomId = :classroomId")
    @Modifying
    void deleteQuestionFromClassroom(int classroomId, int questionId);

}

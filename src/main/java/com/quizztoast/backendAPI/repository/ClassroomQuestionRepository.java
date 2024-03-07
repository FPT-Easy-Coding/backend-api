package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.classroom.ClassroomQuestion;
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
}

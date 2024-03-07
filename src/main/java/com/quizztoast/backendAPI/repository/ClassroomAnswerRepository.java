package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.classroom.ClassroomAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassroomAnswerRepository extends JpaRepository<ClassroomAnswer, Integer> {
    @Query(value = "SELECT a FROM ClassroomAnswer a WHERE a.classroomQuestion.classQuestionId = :questionId")
    public ClassroomAnswer findAnswerByQuestionId(int questionId);
}

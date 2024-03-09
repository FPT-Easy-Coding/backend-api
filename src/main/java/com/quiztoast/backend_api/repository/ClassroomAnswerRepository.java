package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.ClassroomAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassroomAnswerRepository extends JpaRepository<ClassroomAnswer, Integer> {
    @Query(value = "SELECT a FROM ClassroomAnswer a WHERE a.classroomQuestion.classQuestionId = :questionId")
    public ClassroomAnswer findAnswerByQuestionId(int questionId);

    @Query(value = "DELETE FROM ClassroomAnswer a WHERE a.classAnswerId = :answerId")
    void deleteAnswer(int answerId);

    @Query(value = "UPDATE ClassroomAnswer a SET a.content = :content WHERE a.classAnswerId = :answerId")
    void updateAnswer(int answerId, String content);
}

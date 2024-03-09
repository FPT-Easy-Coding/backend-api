package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizQuestionMappingRepository extends JpaRepository<QuizQuestionMapping, QuizQuestionMapping.QuizQuestionMappingId> {
    @Query("SELECT q.id.quizQuestionId FROM QuizQuestionMapping q WHERE q.id.quizId.quizId = :quizId")
    List<QuizQuestion> findQuizQuestionIdsByQuizId(@Param("quizId") int quizId);

    @Query("SELECT COUNT(q.id.quizQuestionId.quizQuestionId) FROM QuizQuestionMapping q WHERE q.id.quizId.quizId = :quizId")
    int countQuizQuestionByQuizID(int quizId);
    @Query("SELECT COUNT(q) FROM QuizQuestionMapping q WHERE q.id.quizId= :quizId")
    int countNumberofquiz(Quiz quizId);

    @Modifying
    @Query("delete from QuizQuestionMapping q where q.id.quizId.quizId = :quizId")
    void deleteByQuizQuestionId(@Param("quizId") long quizId);
}

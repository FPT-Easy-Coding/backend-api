package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizQuestionMappingRepository extends JpaRepository<QuizQuestionMapping, QuizQuestionMapping.QuizQuestionMappingId> {
    @Query("SELECT q.id.quizQuestionId FROM QuizQuestionMapping q WHERE q.id.quizId.quizId = :quizId")
    List<QuizQuestion> findQuizQuestionIdsByQuizId(@Param("quizId") int quizId);

    @Query("SELECT COUNT(q) FROM QuizQuestionMapping q WHERE q.id.quizId= :quizId")
    int countNumberofquiz(Quiz quizId);
}

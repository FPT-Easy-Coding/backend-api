package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.quiz.QuizAnswer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM QuizAnswer qa WHERE qa.quizQuestion.quiz_question_id = :quizQuestionId")
    void deleteByQuizQuestionId(@Param("quizQuestionId") Long quizQuestionId);
}

package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.QuizAnswer;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM QuizAnswer qa WHERE qa.quizQuestion.quizQuestionId = :quizQuestionId")
    void deleteByQuizQuestionId(@Param("quizQuestionId") Long quizQuestionId);
    List<QuizAnswer> findByQuizQuestion(QuizQuestion quizQuestion);
}

package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion ,Long> {

    List<QuizQuestion> findByContentContaining(String content);

    @Modifying
    @Query("DELETE FROM QuizQuestion q WHERE q.quiz_question_id = :quizQuestionId")
    void deleteQuestionById(@Param("quizQuestionId") Long quizQuestionId);
}

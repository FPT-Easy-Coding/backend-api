package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Quiz q WHERE q.quiz_id = :quizId")
    boolean existsById(@Param("quizId") int quizId);
    @Modifying
    @Query("DELETE FROM Quiz q WHERE q.quiz_id = :quizId")
    void deleteQuizById(@Param("quizId") int quizId);


    @Query("SELECT q FROM Quiz q WHERE q.quiz_id = :quizId")
    Quiz getQuizById(@Param("quizId") int quizId);
}

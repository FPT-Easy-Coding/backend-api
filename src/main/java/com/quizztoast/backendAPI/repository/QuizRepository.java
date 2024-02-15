package com.quizztoast.backendAPI.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Quiz q WHERE q.quizId = :quizId")
    boolean existsById(@Param("quizId") int quizId);
    @Modifying
    @Query("DELETE FROM Quiz q WHERE q.quizId = :quizId")
    void deleteQuizById(@Param("quizId") int quizId);


    @Query("SELECT q FROM Quiz q WHERE q.quizId = :quizId")
    Quiz getQuizById(@Param("quizId") int quizId);


    List<Quiz> findByQuizNameContaining(String content);

    @Query("SELECT SUM(q.viewOfQuiz) FROM Quiz q WHERE q.user.userId = :userId")
    Long countViewByUserId(Long userId);
    @Query("SELECT COUNT(q) FROM Quiz q WHERE q.user.userId = :userId")
    Integer countQuizbyUserId(@Param("userId") Long userId);
}

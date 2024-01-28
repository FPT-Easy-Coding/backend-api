package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuizRepository extends JpaRepository<Quiz,Long> {


    @Query("SELECT q FROM Quiz q WHERE LOWER(q.quiz_name) LIKE LOWER(CONCAT('%', :nameOfQuizSet, '%'))")
    List<Quiz> searchQuizzesByName(@Param("nameOfQuizSet") String nameOfQuizSet);
}

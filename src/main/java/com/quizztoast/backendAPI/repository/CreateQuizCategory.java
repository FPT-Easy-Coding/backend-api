package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.CreateQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreateQuizCategory extends JpaRepository<CreateQuiz,CreateQuiz.CreateQ> {
    @Query("SELECT c.id.userId.userId FROM CreateQuiz c WHERE c.id.quizId.quizId = :quizId")
    Long findUserId(int quizId);

}

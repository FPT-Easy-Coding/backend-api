package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.CreateQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreateQuizCategoryRepository extends JpaRepository<CreateQuiz,CreateQuiz.CreateQ> {
    @Query("SELECT c.id.user.userId FROM CreateQuiz c WHERE c.id.quiz.quizId = :quizId")
    Long findUserId(int quizId);

}

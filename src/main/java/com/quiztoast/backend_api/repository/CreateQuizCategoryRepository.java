package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.CreateQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateQuizCategoryRepository extends JpaRepository<CreateQuiz,CreateQuiz.CreateQ> {
    @Query("SELECT c.id.user.userId FROM CreateQuiz c WHERE c.id.quiz.quizId = :quizId")
    Long findUserId(int quizId);

}

package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion ,Long> {

    List<QuizQuestion> findByContentContaining(String content);

    @Modifying
    @Query("DELETE FROM QuizQuestion q WHERE q.quizQuestionId = :quizQuestionId")
    void deleteQuestionById(@Param("quizQuestionId") Long quizQuestionId);

    @Query("SELECT q.content FROM QuizQuestion q WHERE q.quizQuestionId = :quizQuestionId")
    String findQuestionContentById(@Param("quizQuestionId") Long quizQuestionId);

    QuizQuestion findByQuizQuestionId(Long quizQuestionId);

    @Query("SELECT q FROM QuizQuestion q WHERE q.categoryId.categoryId = :id")
    QuizQuestion[] findQuizQuesstionByCategoryId(int id);
}

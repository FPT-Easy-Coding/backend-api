package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuizQuestionMappingRepository extends JpaRepository<QuizQuestionMapping, QuizQuestionMapping.QuizQuestionMappingId> {
    @Query("SELECT q.id.quizQuestion FROM QuizQuestionMapping q WHERE q.id.quiz.quizId = :quizId")
    List<QuizQuestion> findQuizQuestionIdsByQuizId(@Param("quizId") int quizId);

    @Query("SELECT COUNT(q.id.quizQuestion.quizQuestionId) FROM QuizQuestionMapping q WHERE q.id.quiz.quizId = :quizId")
    int countQuizQuestionByQuizID(int quizId);
    @Query("SELECT COUNT(q) FROM QuizQuestionMapping q WHERE q.id.quiz= :quizId")
    int countNumberOfQuiz(Quiz quizId);

    @Modifying
    @Query("delete from QuizQuestionMapping q where q.id.quiz.quizId = :quizId")
    void deleteByQuizId(@Param("quizId") long quizId);
    @Modifying
    @Query("delete from QuizQuestionMapping q where q.id.quizQuestion.quizQuestionId = :quizQuestionId")
    void deleteByQuizQuestionId(long quizQuestionId);
}

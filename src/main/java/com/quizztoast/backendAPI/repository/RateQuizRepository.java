package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.RateQuiz;
import com.quizztoast.backendAPI.model.payload.response.RateQuizResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RateQuizRepository extends JpaRepository<RateQuiz,RateQuiz.RateQuizId> {


    @Query("SELECT AVG(r.id.rate) FROM RateQuiz r WHERE r.id.quizId.quizId = :quizId")
    Float averageRateOfQuiz(@Param("quizId") int quizId);
    @Query("SELECT r FROM RateQuiz r WHERE r.id.quizId.quizId = :quizId and r.id.userId.userId = :userId")
    RateQuiz findByQuizIdAndUserId(@Param("quizId") int quizId, @Param("userId")long userId);
    @Modifying
    @Query("DELETE FROM RateQuiz r WHERE r.id.quizId.quizId = :quizId AND r.id.userId.userId = :userId")
    void deleteByQuizIdAndUserId(@Param("quizId") int quizId, @Param("userId") long userId);

}

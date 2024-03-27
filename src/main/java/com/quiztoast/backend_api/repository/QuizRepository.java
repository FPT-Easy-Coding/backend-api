package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.naming.directory.SearchResult;
import java.util.Collection;
import java.util.List;


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


    @Query("SELECT c.quizId FROM Quiz c WHERE c.user.userId = :userId")
    List<Integer> findQuizId(Long userId);

    @Query("SELECT q FROM Quiz q WHERE q.user.userId = :userId")
    List<Quiz> findQuizByUserId(Long userId);
    @Query("SELECT COUNT(q) FROM Quiz q WHERE q.category.categoryId = :categoryId")
    int findTotalQuizByCategory(int categoryId);

    @Query("SELECT q FROM Quiz q WHERE q.category.categoryId = :categoryId")
    List<Quiz> findQuizByCategory(int categoryId);
    @Query("SELECT q.rate FROM Quiz q WHERE q.quizId = :quizId")
    Integer findRateByQuizId(int quizId);


    @Query("SELECT q FROM Quiz q WHERE q.quizName LIKE %:keywords%")
    List<Quiz> searchByName(String keywords);
}

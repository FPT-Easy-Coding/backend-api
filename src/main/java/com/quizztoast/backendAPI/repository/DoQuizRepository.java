package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.DoQuiz;
import com.quizztoast.backendAPI.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.quizztoast.backendAPI.model.entity.quiz.DoQuiz.DoQuizId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoQuizRepository extends JpaRepository<DoQuiz, DoQuizId> {
    List<DoQuiz> findByIdUser(User user);
}

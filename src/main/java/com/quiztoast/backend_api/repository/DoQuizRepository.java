package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.DoQuiz;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.quiztoast.backend_api.model.entity.quiz.DoQuiz.DoQuizId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoQuizRepository extends JpaRepository<DoQuiz, DoQuizId> {
    List<DoQuiz> findByIdUser(User user);
}

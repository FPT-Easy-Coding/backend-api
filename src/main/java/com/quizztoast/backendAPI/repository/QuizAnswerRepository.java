package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long> {
}

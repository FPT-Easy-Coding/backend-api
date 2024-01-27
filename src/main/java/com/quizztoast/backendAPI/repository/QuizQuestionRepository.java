package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion ,Long> {
}

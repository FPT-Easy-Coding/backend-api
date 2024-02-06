package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestionMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionMappingRepository extends JpaRepository<QuizQuestionMapping, QuizQuestionMapping.QuizQuestionMappingId> {

}

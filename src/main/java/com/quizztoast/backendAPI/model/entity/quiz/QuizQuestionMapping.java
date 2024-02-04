package com.quizztoast.backendAPI.model.entity.quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz_question_mapping")
public class QuizQuestionMapping {

    @EmbeddedId
    private QuizQuestionMappingId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class QuizQuestionMappingId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quizId;

        @ManyToOne
        @JoinColumn(name = "quiz_question_id")
        private QuizQuestion quizQuestionId;

        public void setQuizId(Quiz quiz) {
            this.quizId = quiz;
        }

        public void setQuizQuestionId(QuizQuestion quizQuestion) {
            this.quizQuestionId = quizQuestion;
        }

        // Constructors, getters, setters, etc.
    }

}
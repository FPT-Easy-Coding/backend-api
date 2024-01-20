package com.quizztoast.backendAPI.model.quiz;

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
@Table
public class QuizQuestionMapping {

    @EmbeddedId
    private QuizQuestionMappingId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public class QuizQuestionMappingId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quiz_id;

        @ManyToOne
        @JoinColumn(name = "quiz_question_id")
        private QuizQuestion quiz_question_id;

        // Constructors, getters, setters, etc.
    }

}
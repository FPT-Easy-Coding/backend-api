package com.quizztoast.backendAPI.model.entity.quiz;

import com.quizztoast.backendAPI.model.entity.user.User;
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
public class DoQuiz {

    @EmbeddedId
    private DoQuizId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class DoQuizId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_do_id")
        private User userId;

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quizId;

        // Constructors, getters, setters, etc.
    }

}
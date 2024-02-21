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
@Table(name = "create_quiz")
public class CreateQuiz {

    @EmbeddedId
    private CreateQ id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class CreateQ implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_create_id")
        private User userId;

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quizId;
        public void setQuizId(Quiz quiz) {
            this.quizId = quiz;
        }

        public void setUserId(User userId) {
            this.userId = userId;
        }
        // Constructors, getters, setters, etc.
    }

}
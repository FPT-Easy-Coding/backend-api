package com.quizztoast.backendAPI.model.entity.quiz;

import com.quizztoast.backendAPI.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rate_quiz")
public class RateQuiz {

    @EmbeddedId
    private RateQuizId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class RateQuizId implements Serializable {

        @Setter
        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quizId;

        @Setter
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User userId;

        private float rate;

        public Quiz getQuizId() {
            return quizId;
        }

        public void setQuizId(Quiz quizId) {
            this.quizId = quizId;
        }

        public User getUserId() {
            return userId;
        }

        public void setUserId(User userId) {
            this.userId = userId;
        }

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

    }

}
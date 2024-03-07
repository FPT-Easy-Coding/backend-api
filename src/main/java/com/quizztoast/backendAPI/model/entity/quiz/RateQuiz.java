package com.quizztoast.backendAPI.model.entity.quiz;

import com.quizztoast.backendAPI.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

        private LocalDateTime createAt;


        private float rate;

        public int getQuizId() {
            return quizId.getQuizId();
        }

        public void setQuizId(Quiz quizId) {
            this.quizId = quizId;
        }

        public long getUserId() {
            return userId.getUserId();
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

        public LocalDateTime getCreateAt() {
            return createAt;
        }

        public void setCreateAt() {
            this.createAt = LocalDateTime.now();
        }

    }

}
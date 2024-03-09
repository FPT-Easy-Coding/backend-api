package com.quiztoast.backend_api.model.entity.quiz;

import com.quiztoast.backend_api.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

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
    @Getter
    @Setter
    public static class CreateQ implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_create_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quiz;

    }
}
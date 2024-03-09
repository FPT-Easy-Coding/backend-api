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
@Table(name = "do_quiz")
public class DoQuiz {

    @EmbeddedId
    private DoQuizId id;


    @Setter
    @Getter
    @Embeddable
    public static class DoQuizId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_do_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quiz;

    }
}


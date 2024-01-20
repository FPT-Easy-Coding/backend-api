package com.quizztoast.backendAPI.model.quiz;

import com.quizztoast.backendAPI.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table
@Data
public class DoQuiz {

    @EmbeddedId
    private DoQuizid id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public class DoQuizid implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_doquiz_id")
        private User user_id;

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quiz_id;

        // Constructors, getters, setters, etc.
    }

}
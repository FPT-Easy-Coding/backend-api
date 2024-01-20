package com.quizztoast.backendAPI.model.quiz;

import com.quizztoast.backendAPI.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table
@Data
public class CreateQuiz {

    @EmbeddedId
    private CreateQ id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public class CreateQ implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_create_id")
        private User user_id;

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quiz_id;

        // Constructors, getters, setters, etc.
    }

}
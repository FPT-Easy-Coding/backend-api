package com.quizztoast.backendAPI.model.inClass;

import com.quizztoast.backendAPI.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Table
@Data
public class UserBelongClass {

    @EmbeddedId
    private UserBelongClassId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public class UserBelongClassId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "class_id")
        private Class classEntity;

        // Constructors, getters, setters, etc.
    }

}
package com.quizztoast.backendAPI.model.entity.inClass;

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
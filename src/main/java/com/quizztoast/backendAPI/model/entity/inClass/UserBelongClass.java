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
@Table(name = "user_belong_class")
public class UserBelongClass {

    @EmbeddedId
    private UserBelongClassId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class UserBelongClassId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User userEntity;

        @ManyToOne
        @JoinColumn(name = "class_id")
        private Class classEntity;

        // Constructors, getters, setters, etc.
    }

}
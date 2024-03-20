package com.quiztoast.backend_api.model.entity.classroom;

import com.quiztoast.backend_api.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_belong_class")
public class UserBelongClassroom {

    @EmbeddedId
    private UserBelongClassroomId id;

    // Constructors, getters, setters, etc.
    @Setter
    @Getter
    @Embeddable
    public static class UserBelongClassroomId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "class_id")
        private Classroom classroom;

        public UserBelongClassroomId(User user, Classroom classroom) {
            this.user = user;
            this.classroom = classroom;
        }

        public UserBelongClassroomId() {

        }

        // Constructors, getters, setters, etc.
    }

}
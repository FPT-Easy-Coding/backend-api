package com.quizztoast.backendAPI.model.entity.classroom;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz_belong_class")
public class QuizBelongClassroom {
    @EmbeddedId
    private QuizBeLongClassroomId id;

    // Constructors, getters, setters, etc.
    @Setter
    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizBeLongClassroomId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quiz;

        @ManyToOne
        @JoinColumn(name = "class_id")
        private Classroom classroom;
    }
}

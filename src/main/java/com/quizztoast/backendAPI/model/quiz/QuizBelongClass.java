package com.quizztoast.backendAPI.model.quiz;

import com.quizztoast.backendAPI.model.exam.Exam;
import com.quizztoast.backendAPI.model.inClass.Class;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table
@Data
public class QuizBelongClass {

    @EmbeddedId
    private QuizBeLongClassId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public class QuizBeLongClassId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "exam_id")
        private Exam exam_id;

        @ManyToOne
        @JoinColumn(name = "class_id")
        private Class class_id;

        // Constructors, getters, setters, etc.
    }

}
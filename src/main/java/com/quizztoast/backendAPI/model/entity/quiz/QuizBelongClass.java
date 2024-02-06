package com.quizztoast.backendAPI.model.entity.quiz;

import com.quizztoast.backendAPI.model.entity.exam.Exam;
import com.quizztoast.backendAPI.model.entity.inClass.Class;
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
@Table(name = "quiz_belong_class")
public class QuizBelongClass {

    @EmbeddedId
    private QuizBeLongClassId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class QuizBeLongClassId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "exam_id")
        private Exam examId;

        @ManyToOne
        @JoinColumn(name = "class_id")
        private Class classId;

        // Constructors, getters, setters, etc.
    }

}
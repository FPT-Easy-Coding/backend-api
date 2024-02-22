package com.quizztoast.backendAPI.model.entity.classroom;

import com.quizztoast.backendAPI.model.entity.exam.Exam;
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
@Table(name = "exam_belong_class")
public class ExamBelongClassroom {

    @EmbeddedId
    private ExamBeLongClassroomId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class ExamBeLongClassroomId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "exam_id")
        private Exam exam;

        @ManyToOne
        @JoinColumn(name = "class_id")
        private Classroom classroom;

        // Constructors, getters, setters, etc.
    }

}
package com.quizztoast.backendAPI.model.entity.exam;

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
@Table(name = "do_exam")
public class DoExam {

    @EmbeddedId
    private DoExamId id;

    // Constructors, getters, setters, etc.
    @Embeddable
    public static class DoExamId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "exam_id")
        private Exam examId;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User userId;

        // Constructors, getters, setters, etc.
    }

}
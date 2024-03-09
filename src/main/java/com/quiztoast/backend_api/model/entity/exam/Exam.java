package com.quiztoast.backend_api.model.entity.exam;

import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", nullable = false)
    private int examId;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quizId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "craeted_at", nullable = false)
    private LocalDateTime craetedAt;

    @Column(name = "state", nullable = false, length = 255)
    private String state;


}

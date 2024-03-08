package com.quizztoast.backendAPI.model.entity.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "quiz_answer")
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_answer_id", nullable = false)
    private long quizAnswerId;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    @JsonIgnore
    private QuizQuestion quizQuestion;  // Thay đổi tên trường này

    @Column(name= "content" ,nullable = false, length = 255)
    private String content;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name= "created_at" ,nullable = false)
    private LocalDateTime createdAt;

}

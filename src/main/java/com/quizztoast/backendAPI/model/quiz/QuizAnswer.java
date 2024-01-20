package com.quizztoast.backendAPI.model.quiz;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quiz_answer_id;
    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quiz_question_id;
    @Column(name= "content" ,nullable = false,length = 255)

    private String content;
    @Column(name= "is_correct" ,nullable = false)

    private Boolean is_correct;
    @Column(name= "created_at" ,nullable = false)

    private LocalDateTime created_at;


}

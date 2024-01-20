package com.quizztoast.backendAPI.model.quiz;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long quiz_question_id;
    @Column(name= "created_at" ,nullable = false)

    private LocalDateTime created_at;
    @Column(name= "content" ,nullable = false,length = 1000)

    private String content;


}

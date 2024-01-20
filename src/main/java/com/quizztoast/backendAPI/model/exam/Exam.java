package com.quizztoast.backendAPI.model.exam;

import com.quizztoast.backendAPI.model.quiz.Quiz;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int exam_id;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
   private Quiz quiz_id;

    @Column(name= "user_id" ,nullable = false)

    private Long user_id;
    @Column(name= "craeted_id" ,nullable = false)

    private LocalDateTime craeted_id;
    @Column(name= "state" ,nullable = false,length = 255)

    private String state;


}

package com.quizztoast.backendAPI.model.quiz;

import com.quizztoast.backendAPI.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quiz_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @Column(name= "class_id" ,nullable = false)
    private int class_id;

@ManyToOne
@JoinColumn(name = "category_id")
    private Category category_id; // Change the property name to match your entity

    @Column(name= "quiz_name" ,nullable = false)
    private String quiz_name;

    @Column(name= "rate" ,nullable = false,length = 255)
    private int rate;

    @Column(name= "created_at" ,nullable = false)
    private LocalDateTime created_at;

    @Column(name= "quiz_ques_id" ,nullable = false)
    private int quiz_ques_id;
}

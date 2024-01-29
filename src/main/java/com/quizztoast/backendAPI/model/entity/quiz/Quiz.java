package com.quizztoast.backendAPI.model.entity.quiz;

import com.quizztoast.backendAPI.model.entity.user.User;
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
@Table
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quiz_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name= "class_id" ,nullable = false)
    private int class_id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Change the property name to match your entity

    @Column(name = "quiz_name", nullable = false)
    private String quiz_name;

    @Column(name= "rate" ,nullable = false,length = 255)
    private double rate;

    @Column(name= "created_at" ,nullable = false)
    private LocalDateTime created_at;

    @Column(name= "quiz_ques_id" ,nullable = false)
    private int quiz_ques_id;
}

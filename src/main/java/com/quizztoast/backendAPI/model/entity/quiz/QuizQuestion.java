package com.quizztoast.backendAPI.model.entity.quiz;

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
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quiz_question_id;
    @Column(name= "created_at" ,nullable = false)

    private LocalDateTime created_at;
    @Column(name= "content" ,nullable = false,length = 1000)

    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category_id;



}

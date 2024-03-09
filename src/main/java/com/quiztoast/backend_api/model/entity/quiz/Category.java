package com.quiztoast.backend_api.model.entity.quiz;

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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "category_id" ,nullable = false)
    private int categoryId;

    @Column(name= "category_name" ,nullable = false,length = 50)
    private String categoryName;
    private LocalDateTime createAt;
    private int totalQuiz;
}

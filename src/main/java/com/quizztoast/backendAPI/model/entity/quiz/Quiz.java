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
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id", nullable = false)
    private int quizId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name= "class_id" ,nullable = false)
    private int classId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "quiz_name", nullable = false)
    private String quizName;

    @Column(name= "rate" ,nullable = false)
    private double rate;

    @Column(name= "created_at" ,nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "view")
    private Long viewOfQuiz;
    @Column(name = "time_recent_view_quiz")
    private LocalDateTime timeRecentViewQuiz ;

}

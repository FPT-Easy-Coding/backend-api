package com.quizztoast.backendAPI.model.entity.inClass;

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
@Table(name = "class_question")
public class ClassQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_question_id", nullable = false)
    private int classQuestionId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false, length = 1000)
    private LocalDateTime createAt;

    @Column(name = "is_answered", nullable = false)
    private boolean isAnswered;


}

package com.quizztoast.backendAPI.model.entity.inClass;

import com.quizztoast.backendAPI.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class_answer")
public class ClassAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_answer_id", nullable = false)
    private int classAnswerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToOne
    @JoinColumn(name = "class_question_id")
    private ClassQuestion classQuestionId;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;


}

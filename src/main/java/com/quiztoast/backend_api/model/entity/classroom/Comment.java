package com.quiztoast.backend_api.model.entity.classroom;

import com.quiztoast.backend_api.model.entity.user.User;
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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "class_question_id")
    private ClassroomQuestion classroomQuestion;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;


}
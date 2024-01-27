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
@Table
public class ClassQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int class_question_id;


@ManyToOne
@JoinColumn(name = "user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name="class_id")
    private Class class_id;
    @Column(name= "content" ,nullable = false)

    private String content;
    @Column(name= "create_at" ,nullable = false,length = 1000)

    private LocalDateTime create_at;
    @Column(name= "is_answered" ,nullable = false)

    private boolean is_answered;


}

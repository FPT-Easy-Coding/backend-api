package com.quizztoast.backendAPI.model.inClass;

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
@Table
public class ClassAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int class_answer_id ;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @OneToOne
    @JoinColumn(name = "class_question_id")
    private ClassQuestion class_question_id;
    @Column(name= "content" ,nullable = false,length = 1000)

    private String content;


}

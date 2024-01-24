package com.quizztoast.backendAPI.model.inClass;

import com.quizztoast.backendAPI.model.user.User;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;

    //    @Column(name= "user_id" ,nullable = false)
//    private int user_id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name="class_question_id")
    private ClassQuestion class_question_id;
    @Column(name= "content" ,nullable = false,length = 1000)
    private String content;


}
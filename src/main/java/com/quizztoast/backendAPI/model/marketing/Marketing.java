package com.quizztoast.backendAPI.model.marketing;

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
public class Marketing {
    @Id
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image Marketing_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
    @Column(name= "content" ,nullable = false,length = 1000)

    private String content;
    @Column(name= "embed_link" ,nullable = false,length = 255)

    private String embed_link;


}

package com.quizztoast.backendAPI.model.entity.marketing;

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
public class Image {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
 private int image_id;
    @OneToOne
    @JoinColumn(name = "user_id")
private User source_id;

    @Column(name = "webp_img" ,length = 255)
private String webp_img;


}

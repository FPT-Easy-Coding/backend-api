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
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private int imageId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User sourceId;

    @Column(name = "webp_img", length = 255)
    private String webpImg;


}

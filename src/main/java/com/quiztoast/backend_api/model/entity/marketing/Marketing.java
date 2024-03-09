package com.quiztoast.backend_api.model.entity.marketing;

import com.quiztoast.backend_api.model.entity.user.User;
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
@Table(name = "marketing")
public class Marketing {
    @Id
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image MarketingId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "embed_link", nullable = false, length = 255)
    private String embedLink;


}

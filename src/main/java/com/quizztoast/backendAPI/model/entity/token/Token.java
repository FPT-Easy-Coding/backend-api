package com.quizztoast.backendAPI.model.entity.token;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Token {
    @Id
    @GeneratedValue
    private Integer tokenId;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;
    private boolean revoked;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

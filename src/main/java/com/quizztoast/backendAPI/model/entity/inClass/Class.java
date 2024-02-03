package com.quizztoast.backendAPI.model.entity.inClass;

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
@Table(name = "class")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id", nullable = false)
    private int classId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "class_name", nullable = false, length = 255)
    private String className;

    @Column(name = "slug_code", nullable = false, unique = true, length = 255)
    private String slugCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


}

package com.quiztoast.backend_api.model.entity.notification;

import jakarta.persistence.*;


@Entity
@Table(name = "entity_type")
public class EntityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity", nullable = false, unique = true)
    private EntityTypeBase entity;

    @Column(name = "notification_description")
    private String notificationDescription;

    // Constructors, getters, and setters
}

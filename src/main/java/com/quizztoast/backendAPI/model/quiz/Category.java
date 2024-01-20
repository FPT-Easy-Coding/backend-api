package com.quizztoast.backendAPI.model.quiz;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;
    @Column(name= "category_name" ,nullable = false,length = 50)
    private String category_name;



}

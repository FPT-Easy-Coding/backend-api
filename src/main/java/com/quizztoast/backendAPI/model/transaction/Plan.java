package com.quizztoast.backendAPI.model.transaction;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Plan {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plan_id;
@Column(name = "plan_name",nullable = false ,length = 255)
    private String plan_name;
    @Column(name = "plan_price" ,nullable = false)
    private int plan_price;
}

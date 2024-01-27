package com.quizztoast.backendAPI.model.entity.transaction;

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
public class Plan {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plan_id;
@Column(name = "plan_name",nullable = false ,length = 255)
    private String plan_name;
    @Column(name = "plan_price" ,nullable = false)
    private int plan_price;
}

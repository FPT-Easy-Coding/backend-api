package com.quizztoast.backendAPI.model.transaction;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Payment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payment_id;
    @Column(name = "method" ,length = 50)
    private String method ;


}






package com.quizztoast.backendAPI.model.transaction;

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
public class Payment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payment_id;
    @Column(name = "method" ,length = 50)
    private String method ;


}






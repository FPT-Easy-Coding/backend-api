package com.quizztoast.backendAPI.model.entity.transaction;

import com.quizztoast.backendAPI.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan planId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment paymentId;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDate expiredAt;

    @Column(name = "status", nullable = false, length = 255)
    private String status;
}

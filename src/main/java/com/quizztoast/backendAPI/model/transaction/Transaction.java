package com.quizztoast.backendAPI.model.transaction;

import com.quizztoast.backendAPI.model.user.User;
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
@Table
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;

  @ManyToOne
  @JoinColumn(name = "plan_id")
    private Plan plan_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;

@ManyToOne
@JoinColumn(name = "payment_id")
    private Payment payment_id;

    @Column(name = "created_id", nullable = false)
    private LocalDate created_id;

    @Column(name = "expired_at", nullable = false)
    private LocalDate expired_at;

    @Column(name = "status", nullable = false, length = 255)
    private String status;
}

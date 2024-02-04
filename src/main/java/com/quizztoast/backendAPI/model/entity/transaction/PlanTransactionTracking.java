package com.quizztoast.backendAPI.model.entity.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plan_transaction_tracking")
public class PlanTransactionTracking {

    @EmbeddedId
    private PlanTrackId id;

    // Constructors, getters, setters, etc.

    @Embeddable
    public static class PlanTrackId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "transaction_id")
        private Transaction transactionId;

        @Column(name = "date")
        private LocalDate date;

        // Constructors, getters, setters, etc.
    }
}

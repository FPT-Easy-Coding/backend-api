package com.quizztoast.backendAPI.model.transaction;

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
@Table
public class PlanTransactionTracking {

    @EmbeddedId
    private PlanTranid id;

    // Constructors, getters, setters, etc.

    @Embeddable
    public class PlanTranid implements Serializable {

        @ManyToOne
        @JoinColumn(name = "transaction_id")
        private Transaction transaction_id;

        @Column(name = "date" )
        private LocalDate date;

        // Constructors, getters, setters, etc.
    }
}

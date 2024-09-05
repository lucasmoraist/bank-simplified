package com.lucasmoraist.bank_simplified.model;

import com.lucasmoraist.bank_simplified.enums.StatusTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_transaction")
@Table(name = "t_transaction")
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User payer;
    @ManyToOne
    private User payee;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private StatusTransaction status;

    // Código de autorização retornado pelo serviço externo
    private String authorizationCode;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

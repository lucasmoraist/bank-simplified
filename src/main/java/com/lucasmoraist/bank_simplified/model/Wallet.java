package com.lucasmoraist.bank_simplified.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_wallet")
@Table(name = "t_wallet")
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne(mappedBy = "wallet")
    private User user;

    public void plusBalance(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void minusBalance(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }
}

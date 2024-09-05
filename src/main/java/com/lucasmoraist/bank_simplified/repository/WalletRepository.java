package com.lucasmoraist.bank_simplified.repository;

import com.lucasmoraist.bank_simplified.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

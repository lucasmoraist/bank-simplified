package com.lucasmoraist.bank_simplified.repository;

import com.lucasmoraist.bank_simplified.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}

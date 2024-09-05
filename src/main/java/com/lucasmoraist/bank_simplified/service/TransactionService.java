package com.lucasmoraist.bank_simplified.service;

import com.lucasmoraist.bank_simplified.dto.TransferDTO;

public interface TransactionService {
    void createTransaction(TransferDTO dto);
}

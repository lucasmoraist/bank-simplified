package com.lucasmoraist.bank_simplified.service;

import com.lucasmoraist.bank_simplified.dto.DepositDTO;
import com.lucasmoraist.bank_simplified.model.Wallet;

public interface WalletService {
    Wallet deposit(Long id, DepositDTO dto);
}

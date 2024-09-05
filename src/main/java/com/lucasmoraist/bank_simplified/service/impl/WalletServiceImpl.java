package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.DepositDTO;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.WalletRepository;
import com.lucasmoraist.bank_simplified.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    public Wallet deposit(Long id, DepositDTO dto) {
        Wallet wallet = this.getWalletById(id);

        wallet.plusBalance(dto.amount());
        this.repository.save(wallet);

        return wallet;
    }

    private Wallet getWalletById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Wallet not found"));
    }
}

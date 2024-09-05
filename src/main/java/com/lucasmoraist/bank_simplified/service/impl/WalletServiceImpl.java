package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.DepositDTO;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.WalletRepository;
import com.lucasmoraist.bank_simplified.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for depositing money into a wallet.
 *
 * @author lucasmoraist
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    /**
     * Deposits money into a wallet.
     * @param id Wallet ID
     * @param dto Deposit data
     * @return Wallet with updated balance
     */
    public Wallet deposit(Long id, DepositDTO dto) {
        log.info("Starting deposit for wallet with ID: {}", id);
        log.info("Deposit amount: {}", dto.amount());

        Wallet wallet = this.getWalletById(id);

        log.debug("Wallet found: {}", wallet);

        wallet.plusBalance(dto.amount());
        this.repository.save(wallet);

        log.info("Deposit completed. New balance for wallet with ID {}: {}", id, wallet.getBalance());

        return wallet;
    }

    /**
     * Fetches a wallet by its ID.
     * @param id Wallet ID
     * @return Wallet
     */
    private Wallet getWalletById(Long id) {
        log.debug("Fetching wallet with ID: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Wallet with ID {} not found", id);
                    return new ResourceNotFound("Wallet not found");
                });
    }
}

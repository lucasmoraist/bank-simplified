package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.validation.TransferValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Implementation of the transfer validation service.
 *
 * @author lucasmoraist
 */
@Service
@Slf4j
public class TransferValidationImpl implements TransferValidation {

    /**
     * Processes a transfer between two wallets.
     * @param payerWallet wallet that will have its balance decreased
     * @param payeeWallet wallet that will have its balance increased
     * @param amount amount to be transferred
     * @return true if the transfer was successful, false otherwise
     */
    @Override
    public boolean processTransfer(Wallet payerWallet, Wallet payeeWallet, BigDecimal amount) {
        log.info("Processing transfer of amount: {} from payer wallet ID: {} to payee wallet ID: {}",
                amount, payerWallet.getId(), payeeWallet.getId());

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Transfer failed. Invalid amount: {}", amount);
            return false;
        }

        BigDecimal initialPayerBalance = payerWallet.getBalance();
        BigDecimal initialPayeeBalance = payeeWallet.getBalance();

        log.debug("Initial balance of payer wallet ID {}: {}", payerWallet.getId(), initialPayerBalance);
        log.debug("Initial balance of payee wallet ID {}: {}", payeeWallet.getId(), initialPayeeBalance);

        if (initialPayerBalance.compareTo(amount) < 0) {
            log.warn("Transfer failed. Insufficient balance in payer wallet ID {}", payerWallet.getId());
            return false;
        }

        payerWallet.minusBalance(amount);
        payeeWallet.plusBalance(amount);

        boolean payerUpdateSuccess = payerWallet.getBalance().compareTo(initialPayerBalance.subtract(amount)) == 0;
        boolean payeeUpdateSuccess = payeeWallet.getBalance().compareTo(initialPayeeBalance.add(amount)) == 0;

        if (!payerUpdateSuccess || !payeeUpdateSuccess) {
            log.warn("Transfer failed. Reverting balances.");
            payerWallet.plusBalance(amount); // Reverts payer balance
            payeeWallet.minusBalance(amount); // Reverts payee balance
            log.debug("Reverted balance of payer wallet ID {}: {}", payerWallet.getId(), payerWallet.getBalance());
            log.debug("Reverted balance of payee wallet ID {}: {}", payeeWallet.getId(), payeeWallet.getBalance());
            return false;
        }

        log.info("Transfer completed successfully. Updated balance of payer wallet ID {}: {}", payerWallet.getId(), payerWallet.getBalance());
        log.info("Updated balance of payee wallet ID {}: {}", payeeWallet.getId(), payeeWallet.getBalance());

        return true;
    }

}

package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.validation.TransferValidation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferValidationImpl implements TransferValidation {

    @Override
    public boolean processTransfer(Wallet payerWallet, Wallet payeeWallet, BigDecimal amount) {
        BigDecimal initialPayerBalance = payerWallet.getBalance();
        BigDecimal initialPayeeBalance = payeeWallet.getBalance();

        payerWallet.minusBalance(amount);
        payeeWallet.plusBalance(amount);

        boolean payerUpdateSuccess = payerWallet.getBalance().compareTo(initialPayerBalance.subtract(amount)) == 0;
        boolean payeeUpdateSuccess = payeeWallet.getBalance().compareTo(initialPayeeBalance.add(amount)) == 0;

        if (!payerUpdateSuccess || !payeeUpdateSuccess) {
            payerWallet.plusBalance(amount); // Reverte saldo do pagador
            payeeWallet.minusBalance(amount); // Reverte saldo do recebedor
            return false;
        }

        return true;
    }

}

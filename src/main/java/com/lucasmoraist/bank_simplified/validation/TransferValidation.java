package com.lucasmoraist.bank_simplified.validation;

import com.lucasmoraist.bank_simplified.model.Wallet;

import java.math.BigDecimal;

public interface TransferValidation {
    boolean processTransfer(Wallet payerWallet, Wallet payeeWallet, BigDecimal amount);
}

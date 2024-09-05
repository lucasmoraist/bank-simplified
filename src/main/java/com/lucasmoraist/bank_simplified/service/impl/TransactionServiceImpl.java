package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.TransferDTO;
import com.lucasmoraist.bank_simplified.enums.StatusTransaction;
import com.lucasmoraist.bank_simplified.model.Transaction;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.TransactionRepository;
import com.lucasmoraist.bank_simplified.service.TransactionService;
import com.lucasmoraist.bank_simplified.validation.AuthorizationValidation;
import com.lucasmoraist.bank_simplified.validation.PayeeValidation;
import com.lucasmoraist.bank_simplified.validation.PayerValidation;
import com.lucasmoraist.bank_simplified.validation.TransferValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AuthorizationValidation authorizationValidation;
    private final PayeeValidation payeeValidation;
    private final PayerValidation payerValidation;
    private final TransferValidation transferValidation;

    @Override
    @Transactional
    public void createTransaction(TransferDTO dto) {
        User payer = this.payerValidation.validatePayer(dto.payerId(), dto.amount());
        User payee = this.payeeValidation.validatePayee(dto.payeeId());

        this.authorizationValidation.validateAuthorization();

        Wallet payerWallet = payer.getWallet();
        Wallet payeeWallet = payee.getWallet();

        boolean success = this.transferValidation.processTransfer(payerWallet, payeeWallet, dto.amount());

        StatusTransaction status = success ? StatusTransaction.COMPLETED : StatusTransaction.FAILED;

        this.saveTransaction(payer, payee, dto.amount(), status);
    }

    private void saveTransaction(User payer, User payee, BigDecimal amount, StatusTransaction status) {
        Transaction transaction = Transaction.builder()
                .payer(payer)
                .payee(payee)
                .amount(amount)
                .status(status)
                .build();

        this.transactionRepository.save(transaction);
    }
}

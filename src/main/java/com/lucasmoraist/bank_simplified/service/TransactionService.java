package com.lucasmoraist.bank_simplified.service;

import com.lucasmoraist.bank_simplified.dto.TransferDTO;
import com.lucasmoraist.bank_simplified.enums.StatusTransaction;
import com.lucasmoraist.bank_simplified.enums.UserType;
import com.lucasmoraist.bank_simplified.exceptions.AmountException;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.exceptions.TransactionRoles;
import com.lucasmoraist.bank_simplified.model.AuthorizationResponse;
import com.lucasmoraist.bank_simplified.model.Transaction;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.AuthorizationClient;
import com.lucasmoraist.bank_simplified.repository.TransactionRepository;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AuthorizationClient authorizationClient;

    @Transactional
    public void createTransaction(TransferDTO dto) {
        User payer = this.validatePayer(dto.payerId(), dto.amount());
        User payee = this.validatePayee(dto.payeeId());

        this.validateAuthorization();

        Wallet payerWallet = payer.getWallet();
        Wallet payeeWallet = payee.getWallet();

        boolean success = this.processTransfer(payerWallet, payeeWallet, dto.amount());

        StatusTransaction status = success ? StatusTransaction.COMPLETED : StatusTransaction.FAILED;

        this.saveTransaction(payer, payee, dto.amount(), status);
    }

    private User validatePayer(Long payerId, BigDecimal amount){
        User payer = this.userRepository.findById(payerId)
                .orElseThrow(() -> new ResourceNotFound("Payer not found"));

        BigDecimal payerAmount = payer.getWallet().getBalance();
        if (payerAmount.compareTo(amount) < 0) throw new AmountException("Insufficient funds");
        if (payer.getUserType() == UserType.SHOPKEEPER)
            throw new TransactionRoles("Shopkeepers cannot make transactions");

        return payer;
    }

    private User validatePayee(Long payeeId) {
        return this.userRepository.findById(payeeId)
                .orElseThrow(() -> new ResourceNotFound("Payee not found"));
    }

    private void validateAuthorization() {
        AuthorizationResponse authorizationResponse = this.authorizationClient.authorize();
        if (authorizationResponse.getStatus().equals("fail") || !authorizationResponse.getData().isAuthorization()) {
            throw new TransactionRoles("Transaction not authorized");
        }
    }

    private boolean processTransfer(Wallet payerWallet, Wallet payeeWallet, BigDecimal amount) {
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

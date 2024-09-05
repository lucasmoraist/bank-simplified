package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.NotificationDTO;
import com.lucasmoraist.bank_simplified.dto.TransferDTO;
import com.lucasmoraist.bank_simplified.enums.StatusTransaction;
import com.lucasmoraist.bank_simplified.exceptions.NotificationException;
import com.lucasmoraist.bank_simplified.model.Transaction;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.TransactionRepository;
import com.lucasmoraist.bank_simplified.service.NotificationService;
import com.lucasmoraist.bank_simplified.service.TransactionService;
import com.lucasmoraist.bank_simplified.validation.AuthorizationValidation;
import com.lucasmoraist.bank_simplified.validation.PayeeValidation;
import com.lucasmoraist.bank_simplified.validation.PayerValidation;
import com.lucasmoraist.bank_simplified.validation.TransferValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsible for creating transactions between users.
 *
 * @author lucasmoraist
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AuthorizationValidation authorizationValidation;
    private final PayeeValidation payeeValidation;
    private final PayerValidation payerValidation;
    private final TransferValidation transferValidation;
    private final NotificationService notificationService;

    /**
     * Creates a transaction between two users.
     * @param dto the transfer data.
     */
    @Override
    @Transactional
    public void createTransaction(TransferDTO dto) {
        log.info("Starting transaction creation for transferDTO: {}", dto);

        User payer = this.payerValidation.validatePayer(dto.payerId(), dto.amount());
        User payee = this.payeeValidation.validatePayee(dto.payeeId());

        log.debug("Payer validated: {}", payer);
        log.debug("Payee validated: {}", payee);

        this.authorizationValidation.validateAuthorization();
        log.info("Authorization validated successfully");

        Wallet payerWallet = payer.getWallet();
        Wallet payeeWallet = payee.getWallet();

        boolean success = this.transferValidation.processTransfer(payerWallet, payeeWallet, dto.amount());
        StatusTransaction status = success ? StatusTransaction.COMPLETED : StatusTransaction.FAILED;

        log.info("Transfer processed. Status: {}", status);

        this.saveTransaction(payer, payee, dto.amount(), status);
        log.info("Transaction saved with status: {}", status);

        NotificationDTO notificationDTO = new NotificationDTO(
                payee.getEmail(), // Or phone number, depending on the notification target
                dto.amount(),
                payer.getFullName()
        );

        this.sendNotification(success, notificationDTO);
    }

    /**
     * Sends a notification to the payee.
     * @param isSuccess if the transaction was successful.
     * @param dto the notification data.
     */
    private void sendNotification(boolean isSuccess, NotificationDTO dto) {
        if (isSuccess) {
            log.info("Sending notification to: {}", dto.destination());
            boolean notificationSuccess = this.notificationService.sendNotification(dto);

            if (!notificationSuccess) {
                log.error("Failed to send notification to: {}", dto.destination());
                throw new NotificationException();
            }
            log.info("Notification sent successfully to: {}", dto.destination());
        } else {
            log.info("Notification not sent due to failed transaction");
        }
    }

    /**
     * Saves a transaction in the database.
     * @param payer User who is making the payment.
     * @param payee User who is receiving the payment.
     * @param amount The amount of the transaction.
     * @param status The status of the transaction.
     */
    private void saveTransaction(User payer, User payee, BigDecimal amount, StatusTransaction status) {
        log.debug("Saving transaction. Payer: {}, Payee: {}, Amount: {}, Status: {}",
                payer.getFullName(), payee.getFullName(), amount, status);

        Transaction transaction = Transaction.builder()
                .payer(payer)
                .payee(payee)
                .amount(amount)
                .status(status)
                .build();

        this.transactionRepository.save(transaction);
        log.info("Transaction saved with ID: {}", transaction.getId());
    }
}

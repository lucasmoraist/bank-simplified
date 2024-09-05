package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.enums.UserType;
import com.lucasmoraist.bank_simplified.exceptions.AmountException;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.exceptions.TransactionRoles;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import com.lucasmoraist.bank_simplified.validation.PayerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Implementation of the PayerValidation interface.
 *
 * @author lucasmoraist
 */
@Service
@Slf4j
public class PayerValidationImpl implements PayerValidation {

    @Autowired
    private UserRepository userRepository;

    /**
     * Validates the payer's wallet balance and user type.
     * @param payerId ID of the payer to be validated
     * @param amount Amount to be validated
     * @return The payer
     */
    @Override
    public User validatePayer(Long payerId, BigDecimal amount) {
        log.info("Validating payer with ID: {}", payerId);
        log.info("Amount to validate: {}", amount);

        User payer = this.userRepository.findById(payerId)
                .orElseThrow(() -> {
                    log.error("Payer with ID {} not found", payerId);
                    return new ResourceNotFound("Payer not found");
                });

        BigDecimal payerAmount = payer.getWallet().getBalance();
        log.debug("Payer's current wallet balance: {}", payerAmount);

        if (payerAmount.compareTo(amount) < 0) {
            log.error("Insufficient funds for payer with ID {}. Required: {}, Available: {}", payerId, amount, payerAmount);
            throw new AmountException("Insufficient funds");
        }

        if (payer.getUserType() == UserType.SHOPKEEPER) {
            log.error("User with ID {} is a shopkeeper and cannot make transactions", payerId);
            throw new TransactionRoles("Shopkeepers cannot make transactions");
        }

        log.info("Payer with ID {} validated successfully", payerId);
        return payer;
    }

}

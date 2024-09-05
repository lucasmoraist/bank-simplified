package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import com.lucasmoraist.bank_simplified.validation.PayeeValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the payee validation service.
 *
 * @author lucasmoraist
 */
@Service
@Slf4j
public class PayeeValidationImpl implements PayeeValidation {

    @Autowired
    private UserRepository userRepository;

    /**
     * Validates the payee.
     * @param payeeId ID of the payee to be validated
     * @return The payee
     */
    @Override
    public User validatePayee(Long payeeId) {
        log.debug("Validating payee with ID: {}", payeeId);

        return this.userRepository.findById(payeeId)
                .orElseThrow(() -> {
                    log.error("Payee with ID {} not found", payeeId);
                    return new ResourceNotFound("Payee not found");
                });
    }

}

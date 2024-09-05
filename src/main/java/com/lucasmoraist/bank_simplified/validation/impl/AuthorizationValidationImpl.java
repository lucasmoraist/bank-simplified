package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.exceptions.AuthorizationClientException;
import com.lucasmoraist.bank_simplified.exceptions.TransactionRoles;
import com.lucasmoraist.bank_simplified.model.AuthorizationResponse;
import com.lucasmoraist.bank_simplified.repository.AuthorizationClient;
import com.lucasmoraist.bank_simplified.validation.AuthorizationValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the authorization validation service.
 *
 * @author lucasmoraist
 */
@Service
@Slf4j
public class AuthorizationValidationImpl implements AuthorizationValidation {

    @Autowired
    private AuthorizationClient authorizationClient;

    /**
     * Validates the authorization of the transaction.
     */
    @Override
    public void validateAuthorization() {
        log.info("Starting authorization validation.");

        AuthorizationResponse authorizationResponse = this.authorizationClient.authorize();
        log.debug("Authorization response received: {}", authorizationResponse);

        if (authorizationResponse.getStatus().equals("fail") || !authorizationResponse.getData().isAuthorization()) {
            log.error("Authorization failed: {}", authorizationResponse);
            throw new AuthorizationClientException("Transaction not authorized");
        }

        log.info("Authorization validation passed.");
    }

}

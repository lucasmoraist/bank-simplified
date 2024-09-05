package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.exceptions.AuthorizationClientException;
import com.lucasmoraist.bank_simplified.exceptions.TransactionRoles;
import com.lucasmoraist.bank_simplified.model.AuthorizationResponse;
import com.lucasmoraist.bank_simplified.repository.AuthorizationClient;
import com.lucasmoraist.bank_simplified.validation.AuthorizationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationValidationImpl implements AuthorizationValidation {

    @Autowired
    private AuthorizationClient authorizationClient;

    @Override
    public void validateAuthorization() {
        AuthorizationResponse authorizationResponse = this.authorizationClient.authorize();
        if (authorizationResponse.getStatus().equals("fail") || !authorizationResponse.getData().isAuthorization()) {
            throw new AuthorizationClientException("Transaction not authorized");
        }
    }

}

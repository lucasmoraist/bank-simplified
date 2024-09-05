package com.lucasmoraist.bank_simplified.exceptions;

public class AuthorizationClientException extends RuntimeException {

    public AuthorizationClientException(String message) {
        super(message);
    }
}

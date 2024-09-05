package com.lucasmoraist.bank_simplified.exceptions;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String message) {
        super(message);
    }
}

package com.lucasmoraist.bank_simplified.exceptions;

public class CpfDuplicateException extends RuntimeException {
    public CpfDuplicateException() {
        super("CPF already registered");
    }
}

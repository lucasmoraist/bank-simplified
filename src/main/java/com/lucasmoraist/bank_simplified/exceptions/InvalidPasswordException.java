package com.lucasmoraist.bank_simplified.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password. Password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character.");
    }
}

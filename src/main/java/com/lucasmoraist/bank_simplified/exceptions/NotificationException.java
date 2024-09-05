package com.lucasmoraist.bank_simplified.exceptions;

public class NotificationException extends RuntimeException{

    public NotificationException() {
        super("Error sending notification.");
    }

}

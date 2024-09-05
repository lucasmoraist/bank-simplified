package com.lucasmoraist.bank_simplified.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(String message, HttpStatus status) {
}

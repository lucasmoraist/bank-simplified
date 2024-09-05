package com.lucasmoraist.bank_simplified.infra.exception;

import com.lucasmoraist.bank_simplified.exceptions.AmountException;
import com.lucasmoraist.bank_simplified.exceptions.ExceptionDTO;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.exceptions.TransactionRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AmountException.class)
    protected ResponseEntity<ExceptionDTO> handleAmountException(AmountException ex) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(TransactionRoles.class)
    protected ResponseEntity<ExceptionDTO> handleTransactionRoles(TransactionRoles ex) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ResourceNotFound.class)
    protected ResponseEntity<ExceptionDTO> handleResourceNotFound(ResourceNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

}

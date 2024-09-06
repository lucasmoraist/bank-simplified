package com.lucasmoraist.bank_simplified.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.lucasmoraist.bank_simplified.dto.NotificationDTO;
import com.lucasmoraist.bank_simplified.dto.TransferDTO;
import com.lucasmoraist.bank_simplified.enums.UserType;
import com.lucasmoraist.bank_simplified.exceptions.AuthorizationClientException;
import com.lucasmoraist.bank_simplified.exceptions.NotificationException;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.Transaction;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.TransactionRepository;
import com.lucasmoraist.bank_simplified.service.NotificationService;
import com.lucasmoraist.bank_simplified.validation.AuthorizationValidation;
import com.lucasmoraist.bank_simplified.validation.PayeeValidation;
import com.lucasmoraist.bank_simplified.validation.PayerValidation;
import com.lucasmoraist.bank_simplified.validation.TransferValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AuthorizationValidation authorizationValidation;

    @Mock
    private PayeeValidation payeeValidation;

    @Mock
    private PayerValidation payerValidation;

    @Mock
    private TransferValidation transferValidation;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransferDTO transferDTO;
    private User payer;
    private User payee;
    private Wallet payerWallet;
    private Wallet payeeWallet;

    @BeforeEach
    void setUp() {
        transferDTO = new TransferDTO(1L, 2L, BigDecimal.valueOf(100.00));
        payer = new User();
        payee = new User();
        payerWallet = new Wallet();
        payeeWallet = new Wallet();
        payer.setWallet(payerWallet);
        payee.setWallet(payeeWallet);
    }

    @Test
    @DisplayName("Should create transaction successfully when all validations pass and transfer is successful")
    void case01() {
        when(payerValidation.validatePayer(1L, BigDecimal.valueOf(100.00))).thenReturn(payer);
        when(payeeValidation.validatePayee(2L)).thenReturn(payee);
        doNothing().when(authorizationValidation).validateAuthorization();
        when(transferValidation.processTransfer(payerWallet, payeeWallet, BigDecimal.valueOf(100.00))).thenReturn(true);
        when(notificationService.sendNotification(any(NotificationDTO.class))).thenReturn(true);

        transactionService.createTransaction(transferDTO);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(notificationService, times(1)).sendNotification(any(NotificationDTO.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when payer validation fails")
    void case02() {
        when(payerValidation.validatePayer(1L, BigDecimal.valueOf(100.00))).thenReturn(payer);
        when(payeeValidation.validatePayee(2L)).thenReturn(payee);
        doThrow(new RuntimeException("Authorization failed")).when(authorizationValidation).validateAuthorization();

        assertThrows(RuntimeException.class, () -> transactionService.createTransaction(transferDTO));

        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(notificationService, never()).sendNotification(any(NotificationDTO.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when payee validation fails")
    void case03() {
        when(payerValidation.validatePayer(1L, BigDecimal.valueOf(100.00))).thenReturn(payer);
        when(payeeValidation.validatePayee(2L)).thenReturn(payee);
        doNothing().when(authorizationValidation).validateAuthorization();
        when(transferValidation.processTransfer(payerWallet, payeeWallet, BigDecimal.valueOf(100.00))).thenReturn(false);

        transactionService.createTransaction(transferDTO);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(notificationService, never()).sendNotification(any(NotificationDTO.class));
    }

    @Test
    @DisplayName("Should throw NotificationException when notification sending fails after transfer")
    void case04() {
        when(payerValidation.validatePayer(1L, BigDecimal.valueOf(100.00))).thenReturn(payer);
        when(payeeValidation.validatePayee(2L)).thenReturn(payee);
        doNothing().when(authorizationValidation).validateAuthorization();
        when(transferValidation.processTransfer(payerWallet, payeeWallet, BigDecimal.valueOf(100.00))).thenReturn(true);
        when(notificationService.sendNotification(any(NotificationDTO.class))).thenReturn(false);

        assertThrows(NotificationException.class, () -> transactionService.createTransaction(transferDTO));

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(notificationService, times(1)).sendNotification(any(NotificationDTO.class));
    }

}
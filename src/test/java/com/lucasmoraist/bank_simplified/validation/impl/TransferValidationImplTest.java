package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferValidationImplTest {

    @InjectMocks
    private TransferValidationImpl transferValidation;

    @Mock
    private Wallet payerWallet;

    @Mock
    private Wallet payeeWallet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successfully processes a transfer")
    void case01() {
        // Given
        BigDecimal amount = new BigDecimal("100");
        BigDecimal initialPayerBalance = new BigDecimal("500");
        BigDecimal initialPayeeBalance = new BigDecimal("200");

        when(payerWallet.getBalance()).thenReturn(initialPayerBalance);
        when(payeeWallet.getBalance()).thenReturn(initialPayeeBalance);

        // Simulando a atualização correta dos saldos
        doAnswer(invocation -> {
            when(payerWallet.getBalance()).thenReturn(initialPayerBalance.subtract(amount));
            return null;
        }).when(payerWallet).minusBalance(amount);

        doAnswer(invocation -> {
            when(payeeWallet.getBalance()).thenReturn(initialPayeeBalance.add(amount));
            return null;
        }).when(payeeWallet).plusBalance(amount);

        // When
        boolean result = transferValidation.processTransfer(payerWallet, payeeWallet, amount);

        // Then
        verify(payerWallet).minusBalance(amount);
        verify(payeeWallet).plusBalance(amount);
        assertTrue(result);
    }

    @Test
    @DisplayName("Fails the transfer when payer balance is insufficient")
    void case02() {
        // Given
        BigDecimal amount = new BigDecimal("600");
        BigDecimal initialPayerBalance = new BigDecimal("500");  // Payer has insufficient balance
        BigDecimal initialPayeeBalance = new BigDecimal("200");

        // Mock the wallets
        when(payerWallet.getBalance()).thenReturn(initialPayerBalance);
        when(payeeWallet.getBalance()).thenReturn(initialPayeeBalance);

        // When
        boolean result = transferValidation.processTransfer(payerWallet, payeeWallet, amount);

        // Then
        verify(payerWallet, never()).minusBalance(amount); // Ensure minusBalance was never called
        verify(payeeWallet, never()).plusBalance(amount);  // Ensure plusBalance was never called
        assertFalse(result);
    }

    @Test
    @DisplayName("Reverts transfer when balances are inconsistent after transfer")
    void case03() {
        // Given
        BigDecimal amount = new BigDecimal("100");
        BigDecimal initialPayerBalance = new BigDecimal("500");
        BigDecimal initialPayeeBalance = new BigDecimal("200");

        // Mocking the initial balances of the wallets
        when(payerWallet.getBalance()).thenReturn(initialPayerBalance);
        when(payeeWallet.getBalance()).thenReturn(initialPayeeBalance);

        // Mocking incorrect balance updates
        doAnswer(invocation -> {
            when(payerWallet.getBalance()).thenReturn(initialPayerBalance.subtract(amount).subtract(new BigDecimal("1")));
            return null;
        }).when(payerWallet).minusBalance(amount);

        doAnswer(invocation -> {
            when(payeeWallet.getBalance()).thenReturn(initialPayeeBalance.add(amount).add(new BigDecimal("1")));
            return null;
        }).when(payeeWallet).plusBalance(amount);

        // When
        boolean result = transferValidation.processTransfer(payerWallet, payeeWallet, amount);

        // Then
        verify(payerWallet).plusBalance(amount); // Reverts payer balance
        verify(payeeWallet).minusBalance(amount); // Reverts payee balance
        assertFalse(result);
    }

    @Test
    @DisplayName("Fails transfer if amount is zero or negative")
    void case04() {
        BigDecimal zeroAmount = BigDecimal.ZERO;
        BigDecimal negativeAmount = new BigDecimal("-100");

        // Mock the wallet balances
        lenient().when(payerWallet.getBalance()).thenReturn(new BigDecimal("500"));
        lenient().when(payeeWallet.getBalance()).thenReturn(new BigDecimal("200"));

        // Test with zero amount
        boolean resultZero = transferValidation.processTransfer(payerWallet, payeeWallet, zeroAmount);

        // Then
        verify(payerWallet, never()).minusBalance(zeroAmount); // Ensure minusBalance was never called
        verify(payeeWallet, never()).plusBalance(zeroAmount);  // Ensure plusBalance was never called
        assertFalse(resultZero);  // Transfer should fail

        // Test with negative amount
        boolean resultNegative = transferValidation.processTransfer(payerWallet, payeeWallet, negativeAmount);

        // Then
        verify(payerWallet, never()).minusBalance(negativeAmount); // Ensure minusBalance was never called
        verify(payeeWallet, never()).plusBalance(negativeAmount);  // Ensure plusBalance was never called
        assertFalse(resultNegative);
    }

}
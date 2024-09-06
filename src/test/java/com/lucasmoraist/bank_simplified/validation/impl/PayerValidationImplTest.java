package com.lucasmoraist.bank_simplified.validation.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import com.lucasmoraist.bank_simplified.enums.UserType;
import com.lucasmoraist.bank_simplified.exceptions.AmountException;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.exceptions.TransactionRoles;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PayerValidationImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PayerValidationImpl payerValidation;

    @Test
    @DisplayName("Successfully validate payer with sufficient balance and valid user type")
    void case01() {
        // Arrange
        Long payerId = 1L;
        BigDecimal amount = BigDecimal.valueOf(50.00);

        User payer = new User();
        payer.setUserType(UserType.COMMON);
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100.00));
        payer.setWallet(wallet);

        when(userRepository.findById(payerId)).thenReturn(Optional.of(payer));

        // Act
        User result = payerValidation.validatePayer(payerId, amount);

        // Assert
        assertNotNull(result);
        assertEquals(payer, result);
        verify(userRepository, times(1)).findById(payerId);
    }

    @Test
    @DisplayName("Fail to validate payer due to insufficient funds")
    void case02() {
        // Arrange
        Long payerId = 2L;
        BigDecimal amount = BigDecimal.valueOf(150.00);

        User payer = new User();
        payer.setUserType(UserType.COMMON);
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100.00));
        payer.setWallet(wallet);

        when(userRepository.findById(payerId)).thenReturn(Optional.of(payer));

        // Act & Assert
        AmountException thrown = assertThrows(AmountException.class, () -> {
            payerValidation.validatePayer(payerId, amount);
        });
        assertEquals("Insufficient funds", thrown.getMessage());
        verify(userRepository, times(1)).findById(payerId);
    }

    @Test
    @DisplayName("Fail to validate payer because payer is a shopkeeper")
    void case03() {
        // Arrange
        Long payerId = 3L;
        BigDecimal amount = BigDecimal.valueOf(50.00);

        User payer = new User();
        payer.setUserType(UserType.SHOPKEEPER);
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100.00));
        payer.setWallet(wallet);

        when(userRepository.findById(payerId)).thenReturn(Optional.of(payer));

        // Act & Assert
        TransactionRoles thrown = assertThrows(TransactionRoles.class, () -> {
            payerValidation.validatePayer(payerId, amount);
        });
        assertEquals("Shopkeepers cannot make transactions", thrown.getMessage());
        verify(userRepository, times(1)).findById(payerId);
    }

    @Test
    @DisplayName("Fail to validate payer due to payer not found")
    void case04() {
        // Arrange
        Long payerId = 4L;
        BigDecimal amount = BigDecimal.valueOf(50.00);

        when(userRepository.findById(payerId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFound thrown = assertThrows(ResourceNotFound.class, () -> {
            payerValidation.validatePayer(payerId, amount);
        });
        assertEquals("Payer not found", thrown.getMessage());
        verify(userRepository, times(1)).findById(payerId);
    }

}
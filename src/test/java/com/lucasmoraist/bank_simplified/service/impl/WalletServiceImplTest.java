package com.lucasmoraist.bank_simplified.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.lucasmoraist.bank_simplified.dto.DepositDTO;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.WalletRepository;
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
import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    @Test
    @DisplayName("Should deposit money into wallet successfully")
    void case01() {
        // Arrange
        Wallet wallet = new Wallet();
        wallet.setId(1L); // Certifique-se de que o ID está definido
        wallet.setBalance(BigDecimal.ZERO);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DepositDTO depositDTO = new DepositDTO(BigDecimal.valueOf(100.00));

        // Act
        Wallet updatedWallet = walletService.deposit(1L, depositDTO);

        // Assert
        assertNotNull(updatedWallet);
        assertEquals(BigDecimal.valueOf(100.00), updatedWallet.getBalance());
        verify(walletRepository).findById(1L);
        verify(walletRepository).save(updatedWallet);
    }



    @Test
    @DisplayName("Should throw ResourceNotFound when wallet is not found")
    void case02() {
        // Arrange
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());
        DepositDTO depositDTO = new DepositDTO(BigDecimal.valueOf(100.00));

        // Act & Assert
        assertThrows(ResourceNotFound.class, () -> walletService.deposit(1L, depositDTO));
    }
}
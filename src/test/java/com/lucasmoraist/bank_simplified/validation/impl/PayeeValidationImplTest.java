package com.lucasmoraist.bank_simplified.validation.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PayeeValidationImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PayeeValidationImpl payeeValidation;

    @Test
    @DisplayName("Successfully validate payee that exists")
    void case01() {
        // Arrange
        Long payeeId = 1L;

        User payee = new User();
        payee.setId(payeeId);

        when(userRepository.findById(payeeId)).thenReturn(Optional.of(payee));

        // Act
        User result = payeeValidation.validatePayee(payeeId);

        // Assert
        assertNotNull(result);
        assertEquals(payeeId, result.getId());
        verify(userRepository, times(1)).findById(payeeId);
    }

    @Test
    @DisplayName("Fail to validate payee because payee does not exist")
    void case02() {
        // Arrange
        Long payeeId = 2L;

        when(userRepository.findById(payeeId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFound thrown = assertThrows(ResourceNotFound.class, () -> {
            payeeValidation.validatePayee(payeeId);
        });
        assertEquals("Payee not found", thrown.getMessage());
        verify(userRepository, times(1)).findById(payeeId);
    }

}
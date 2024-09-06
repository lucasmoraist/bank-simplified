package com.lucasmoraist.bank_simplified.validation.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.lucasmoraist.bank_simplified.exceptions.AuthorizationClientException;
import com.lucasmoraist.bank_simplified.model.AuthorizationResponse;
import com.lucasmoraist.bank_simplified.repository.AuthorizationClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorizationValidationImplTest {

    @Mock
    private AuthorizationClient authorizationClient;

    @InjectMocks
    private AuthorizationValidationImpl authorizationValidation;

    @Test
    @DisplayName("Successfully validate authorization when response is successful")
    void case01() {
        // Arrange
        AuthorizationResponse.AuthorizationData authorizationData = new AuthorizationResponse.AuthorizationData(true);
        AuthorizationResponse authorizationResponse = new AuthorizationResponse("success", authorizationData);

        // Mock the behavior of the authorizationClient
        when(authorizationClient.authorize()).thenReturn(authorizationResponse);

        // Act and Assert
        assertDoesNotThrow(() -> authorizationValidation.validateAuthorization());

        // Verify that the authorize method was called exactly once
        verify(authorizationClient, times(1)).authorize();
    }

    @Test
    @DisplayName("Fail to validate authorization when response status is fail")
    void case02() {
        // Arrange
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setStatus("fail");
        authorizationResponse.setData(new AuthorizationResponse.AuthorizationData(false));

        when(authorizationClient.authorize()).thenReturn(authorizationResponse);

        // Act & Assert
        AuthorizationClientException thrown = assertThrows(AuthorizationClientException.class, () -> {
            authorizationValidation.validateAuthorization();
        });
        assertEquals("Transaction not authorized", thrown.getMessage());
        verify(authorizationClient, times(1)).authorize();
    }

    @Test
    @DisplayName("Fail to validate authorization when response data authorization is false")
    void case03() {
        // Arrange
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setStatus("success");
        authorizationResponse.setData(new AuthorizationResponse.AuthorizationData(false));

        when(authorizationClient.authorize()).thenReturn(authorizationResponse);

        // Act & Assert
        AuthorizationClientException thrown = assertThrows(AuthorizationClientException.class, () -> {
            authorizationValidation.validateAuthorization();
        });
        assertEquals("Transaction not authorized", thrown.getMessage());
        verify(authorizationClient, times(1)).authorize();
    }

}
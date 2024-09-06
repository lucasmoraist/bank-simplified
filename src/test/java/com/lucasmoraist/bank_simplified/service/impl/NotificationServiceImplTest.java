package com.lucasmoraist.bank_simplified.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.lucasmoraist.bank_simplified.dto.NotificationDTO;
import com.lucasmoraist.bank_simplified.exceptions.NotificationException;
import com.lucasmoraist.bank_simplified.repository.NotificationClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    @DisplayName("Successfully send notification")
    void case01() {
        // Arrange
        NotificationDTO dto = new NotificationDTO("user@example.com", BigDecimal.valueOf(100.00), "John Doe");

        // Act
        boolean result = notificationService.sendNotification(dto);

        // Assert
        assertTrue(result);
        verify(notificationClient, times(1)).sendNotification(dto);
    }

    @Test
    @DisplayName("Fail to send notification due to NotificationException")
    void case02() {
        // Arrange
        NotificationDTO dto = new NotificationDTO("user@example.com", BigDecimal.valueOf(100.00), "John Doe");
        doThrow(new NotificationException()).when(notificationClient).sendNotification(dto);

        // Act
        boolean result = notificationService.sendNotification(dto);

        // Assert
        assertFalse(result);
        verify(notificationClient, times(1)).sendNotification(dto);
    }

}
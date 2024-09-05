package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.NotificationDTO;
import com.lucasmoraist.bank_simplified.exceptions.NotificationException;
import com.lucasmoraist.bank_simplified.repository.NotificationClient;
import com.lucasmoraist.bank_simplified.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending notifications to users.
 *
 * @author lucasmoraist
 */
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationClient notificationClient;

    /**
     * Sends a notification to the user.
     * @param dto Notification details.
     * @return True if the notification was sent successfully, false otherwise.
     */
    @Override
    public boolean sendNotification(NotificationDTO dto) {
        log.info("Attempting to send notification with details: {}", dto);

        try {
            notificationClient.sendNotification(dto);
            log.info("Notification sent successfully to: {}", dto.destination());
            return true;
        } catch (NotificationException e) {
            log.error("Failed to send notification to: {}. Error: {}", dto.destination(), e.getMessage());
            return false;
        }
    }
}

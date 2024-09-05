package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.NotificationDTO;
import com.lucasmoraist.bank_simplified.repository.NotificationClient;
import com.lucasmoraist.bank_simplified.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationClient notificationClient;

    @Override
    public boolean sendNotification(NotificationDTO dto) {
        try {
            notificationClient.sendNotification(dto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

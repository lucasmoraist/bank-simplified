package com.lucasmoraist.bank_simplified.service;

import com.lucasmoraist.bank_simplified.dto.NotificationDTO;

public interface NotificationService {
    boolean sendNotification(NotificationDTO dto);
}

package com.lucasmoraist.bank_simplified.dto;

import java.math.BigDecimal;

public record NotificationDTO(String destination, BigDecimal amount, String payerName) {
}

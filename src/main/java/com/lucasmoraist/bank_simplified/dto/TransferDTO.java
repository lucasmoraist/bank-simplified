package com.lucasmoraist.bank_simplified.dto;

import java.math.BigDecimal;

public record TransferDTO(Long payerId, Long payeeId, BigDecimal amount) {
}

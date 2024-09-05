package com.lucasmoraist.bank_simplified.validation;

import com.lucasmoraist.bank_simplified.model.User;

import java.math.BigDecimal;

public interface PayerValidation {
    User validatePayer(Long payerId, BigDecimal amount);
}

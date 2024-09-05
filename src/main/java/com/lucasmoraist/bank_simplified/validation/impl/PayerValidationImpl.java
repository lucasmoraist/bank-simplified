package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.enums.UserType;
import com.lucasmoraist.bank_simplified.exceptions.AmountException;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.exceptions.TransactionRoles;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import com.lucasmoraist.bank_simplified.validation.PayerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayerValidationImpl implements PayerValidation {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User validatePayer(Long payerId, BigDecimal amount){
        User payer = this.userRepository.findById(payerId)
                .orElseThrow(() -> new ResourceNotFound("Payer not found"));

        BigDecimal payerAmount = payer.getWallet().getBalance();
        if (payerAmount.compareTo(amount) < 0) throw new AmountException("Insufficient funds");
        if (payer.getUserType() == UserType.SHOPKEEPER)
            throw new TransactionRoles("Shopkeepers cannot make transactions");

        return payer;
    }

}

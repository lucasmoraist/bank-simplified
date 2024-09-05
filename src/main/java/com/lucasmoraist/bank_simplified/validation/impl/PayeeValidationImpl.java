package com.lucasmoraist.bank_simplified.validation.impl;

import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import com.lucasmoraist.bank_simplified.validation.PayeeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayeeValidationImpl implements PayeeValidation {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User validatePayee(Long payeeId) {
        return this.userRepository.findById(payeeId)
                .orElseThrow(() -> new ResourceNotFound("Payee not found"));
    }

}

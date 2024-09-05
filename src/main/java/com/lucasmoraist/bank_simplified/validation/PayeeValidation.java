package com.lucasmoraist.bank_simplified.validation;

import com.lucasmoraist.bank_simplified.model.User;

public interface PayeeValidation {
    User validatePayee(Long payeeId);
}

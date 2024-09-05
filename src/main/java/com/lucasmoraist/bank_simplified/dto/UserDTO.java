package com.lucasmoraist.bank_simplified.dto;

import com.lucasmoraist.bank_simplified.enums.UserType;

public record UserDTO(
        String fullName,
        String cpf,
        String email,
        String password,
        UserType userType
) {
}

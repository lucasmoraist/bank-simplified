package com.lucasmoraist.bank_simplified.service;

import com.lucasmoraist.bank_simplified.dto.UserDTO;
import com.lucasmoraist.bank_simplified.model.User;

public interface UserService {
    User save(UserDTO dto);

    User findById(Long id);

    User findByCpf(String cpf);
}

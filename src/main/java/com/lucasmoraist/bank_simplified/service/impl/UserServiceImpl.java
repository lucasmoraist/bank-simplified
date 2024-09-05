package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.UserDTO;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import com.lucasmoraist.bank_simplified.service.UserService;
import com.lucasmoraist.bank_simplified.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final WalletService walletService;

    @Override
    public User save(UserDTO dto) {

        Wallet wallet = new Wallet();
        User user = User.builder()
                .fullName(dto.fullName())
                .cpf(dto.cpf())
                .email(dto.email())
                .password(dto.password())
                .userType(dto.userType())
                .wallet(wallet)
                .build();

        this.repository.save(user);

        return user;
    }

    @Override
    public User findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    @Override
    public User findByCpf(String cpf) {
        return this.repository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }
}

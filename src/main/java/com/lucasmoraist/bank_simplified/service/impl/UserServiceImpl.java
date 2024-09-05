package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.UserDTO;
import com.lucasmoraist.bank_simplified.exceptions.CpfDuplicateException;
import com.lucasmoraist.bank_simplified.exceptions.InvalidEmailException;
import com.lucasmoraist.bank_simplified.exceptions.InvalidPasswordException;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import com.lucasmoraist.bank_simplified.service.UserService;
import com.lucasmoraist.bank_simplified.service.WalletService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final WalletService walletService;

    @Override
    public User save(UserDTO dto) {
        try {
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

        } catch (DataIntegrityViolationException e) {
            throw new CpfDuplicateException();

        } catch (ConstraintViolationException e) {

            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String fieldName = violation.getPropertyPath().toString();

                if (fieldName.equals("email")) {
                    // Email
                    throw new InvalidEmailException();
                } else if (fieldName.equals("password")) {
                    // Password
                    throw new InvalidPasswordException();
                }
            }
            throw new ConstraintViolationException("Invalid data", e.getConstraintViolations());
        }
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

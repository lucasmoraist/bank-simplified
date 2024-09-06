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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing user operations.
 *
 * @author lucasmoraist
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    /**
     * Saves a new user.
     * @param dto DTO containing the user data.
     * @return The saved user.
     */
    @Override
    public User save(UserDTO dto) {
        log.info("Attempting to save user with CPF: {}", dto.cpf());
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
            log.info("User saved successfully with ID: {}", user.getId());

            return user;

        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving user with CPF: {}", dto.cpf(), e);
            throw new CpfDuplicateException();

        } catch (ConstraintViolationException e) {
            log.error("Constraint violation while saving user with CPF: {}", dto.cpf(), e);

            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String fieldName = violation.getPropertyPath().toString();

                if (fieldName.equals("email")) {
                    log.warn("Invalid email format for user with CPF: {}", dto.cpf());
                    throw new InvalidEmailException();
                } else if (fieldName.equals("password")) {
                    log.warn("Invalid password for user with CPF: {}", dto.cpf());
                    throw new InvalidPasswordException();
                }
            }
            throw new ConstraintViolationException("Invalid data", e.getConstraintViolations());
        }
    }

    /**
     * Finds a user by its ID.
     * @param id The user ID.
     * @return The user found.
     */
    @Override
    public User findById(Long id) {
        log.info("Fetching user with ID: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", id);
                    return new ResourceNotFound("User not found");
                });
    }

    /**
     * Finds a user by its CPF.
     * @param cpf The user CPF.
     * @return The user found.
     */
    @Override
    public User findByCpf(String cpf) {
        log.info("Fetching user with CPF: {}", cpf);
        return this.repository.findByCpf(cpf)
                .orElseThrow(() -> {
                    log.error("User with CPF {} not found", cpf);
                    return new ResourceNotFound("User not found");
                });
    }
}

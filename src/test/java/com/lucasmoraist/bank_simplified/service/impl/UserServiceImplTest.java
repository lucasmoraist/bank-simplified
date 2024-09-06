package com.lucasmoraist.bank_simplified.service.impl;

import com.lucasmoraist.bank_simplified.dto.UserDTO;
import com.lucasmoraist.bank_simplified.enums.UserType;
import com.lucasmoraist.bank_simplified.exceptions.CpfDuplicateException;
import com.lucasmoraist.bank_simplified.exceptions.InvalidEmailException;
import com.lucasmoraist.bank_simplified.exceptions.InvalidPasswordException;
import com.lucasmoraist.bank_simplified.exceptions.ResourceNotFound;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.Set;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Successfully save user")
    void case01() {
        // Arrange
        UserDTO dto = new UserDTO("John Doe", "12345678900", "john@example.com", "Password1", UserType.COMMON);
        Wallet wallet = new Wallet();
        User user = User.builder()
                .fullName(dto.fullName())
                .cpf(dto.cpf())
                .email(dto.email())
                .password(dto.password())
                .userType(dto.userType())
                .wallet(wallet)
                .build();

        when(repository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.save(dto);

        // Assert
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getCpf(), savedUser.getCpf());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Fail to save user due to DataIntegrityViolationException")
    void case02() {
        // Arrange
        UserDTO dto = new UserDTO("John Doe", "12345678900", "john@example.com", "Password1", UserType.COMMON);
        when(repository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("Data integrity violation"));

        // Act & Assert
        assertThrows(CpfDuplicateException.class, () -> userService.save(dto));
    }

    @Test
    @DisplayName("Fail to save user due to ConstraintViolationException for email")
    void case03() {
        // Arrange
        UserDTO dto = new UserDTO("John Doe", "12345678900", "invalid-email", "Password1", UserType.COMMON);
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(mock(Path.class));
        when(violation.getPropertyPath().toString()).thenReturn("email");
        ConstraintViolationException exception = new ConstraintViolationException("Invalid data", Set.of(violation));
        when(repository.save(any(User.class))).thenThrow(exception);

        // Act & Assert
        assertThrows(InvalidEmailException.class, () -> userService.save(dto));
    }

    @Test
    @DisplayName("Fail to save user due to ConstraintViolationException for password")
    void case04() {
        // Arrange
        UserDTO dto = new UserDTO("John Doe", "12345678900", "john@example.com", "short", UserType.COMMON);
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(mock(Path.class));
        when(violation.getPropertyPath().toString()).thenReturn("password");
        ConstraintViolationException exception = new ConstraintViolationException("Invalid data", Set.of(violation));
        when(repository.save(any(User.class))).thenThrow(exception);

        // Act & Assert
        assertThrows(InvalidPasswordException.class, () -> userService.save(dto));
    }

    @Test
    @DisplayName("Successfully find user by ID")
    void case05() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        verify(repository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Fail to find user by ID when not found")
    void case06() {
        // Arrange
        Long userId = 1L;
        when(repository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFound.class, () -> userService.findById(userId));
    }

    @Test
    @DisplayName("Successfully find user by CPF")
    void case07() {
        // Arrange
        String cpf = "12345678900";
        User user = new User();
        user.setCpf(cpf);
        when(repository.findByCpf(cpf)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByCpf(cpf);

        // Assert
        assertNotNull(foundUser);
        assertEquals(cpf, foundUser.getCpf());
        verify(repository, times(1)).findByCpf(cpf);
    }

    @Test
    @DisplayName("Fail to find user by CPF when not found")
    void case08() {
        // Arrange
        String cpf = "12345678900";
        when(repository.findByCpf(cpf)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFound.class, () -> userService.findByCpf(cpf));
    }

}
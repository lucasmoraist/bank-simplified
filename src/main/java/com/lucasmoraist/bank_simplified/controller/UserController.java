package com.lucasmoraist.bank_simplified.controller;

import com.lucasmoraist.bank_simplified.dto.UserDTO;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> savePerson(@Valid @RequestBody UserDTO dto) {
        User user = this.userService.save(dto);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = this.userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<User> findByCpf(@PathVariable String cpf) {
        User user = this.userService.findByCpf(cpf);
        return ResponseEntity.ok().body(user);
    }

}

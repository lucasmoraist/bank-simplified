package com.lucasmoraist.bank_simplified.controller;

import com.lucasmoraist.bank_simplified.dto.TransferDTO;
import com.lucasmoraist.bank_simplified.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransferDTO dto) {
        this.service.createTransaction(dto);
        return ResponseEntity.ok().build();
    }

}

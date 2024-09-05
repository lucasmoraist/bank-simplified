package com.lucasmoraist.bank_simplified.controller;

import com.lucasmoraist.bank_simplified.dto.DepositDTO;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {

    @Autowired
    private WalletService service;

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<Wallet> deposit(@PathVariable Long id, @RequestBody DepositDTO dto) {
        Wallet wallet = this.service.deposit(id, dto);
        return ResponseEntity.ok(wallet);
    }

}

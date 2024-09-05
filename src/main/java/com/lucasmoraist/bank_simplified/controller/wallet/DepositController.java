package com.lucasmoraist.bank_simplified.controller.wallet;

import com.lucasmoraist.bank_simplified.dto.DepositDTO;
import com.lucasmoraist.bank_simplified.model.Wallet;
import com.lucasmoraist.bank_simplified.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/wallet")
@Tag(name = "V2")
@Slf4j
public class DepositController {

    @Autowired
    private WalletService service;

    @Operation(summary = "Deposit", description = "Deposit money into a wallet")
    @Parameter(name = "id", description = "Wallet's id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Wallet.class))),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                    	"message": "Wallet not found",
                    	"status": "NOT_FOUND"
                    }
                    """))),
    })
    @PatchMapping("/{id}/deposit")
    public ResponseEntity<Wallet> deposit(@PathVariable Long id, @RequestBody DepositDTO dto) {
        log.info("Depositing into wallet with id: {}", id);
        log.info("Amount to deposit: {}", dto.amount());
        Wallet wallet = this.service.deposit(id, dto);
        log.info("Deposit successful: {}", wallet);
        return ResponseEntity.ok(wallet);
    }

}

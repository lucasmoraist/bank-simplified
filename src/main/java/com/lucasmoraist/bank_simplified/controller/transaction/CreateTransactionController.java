package com.lucasmoraist.bank_simplified.controller.transaction;

import com.lucasmoraist.bank_simplified.dto.TransferDTO;
import com.lucasmoraist.bank_simplified.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for creating transactions between two wallets
 *
 * @see TransferDTO
 * @see TransactionService
 *
 * @author lucasmoraist
 */
@RestController
@RequestMapping("/v1/transactions")
@Tag(name = "V1")
@Slf4j
public class CreateTransactionController {

    @Autowired
    private TransactionService service;

    @Operation(summary = "Create a transaction", description = "Create a transaction between two wallets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "404", description = "Payer or payee not found", content = @Content(mediaType = "application/json",schema = @Schema(example = """
                    {
                    	"message": "Payer not found",
                    	"status": "NOT_FOUND"
                    }
                    """
            ))),
            @ApiResponse(responseCode = "400", description = "Insufficient funds", content = @Content(mediaType = "application/json",schema = @Schema(example = """
                    {
                    	"message": "Insufficient funds",
                    	"status": "BAD_REQUEST"
                    }
                    """
            ))),
            @ApiResponse(responseCode = "403", description = "Shopkeepers cannot make transactions", content = @Content(mediaType = "application/json",schema = @Schema(example = """
                    {
                    	"message": "Shopkeepers cannot make transactions",
                    	"status": "FORBIDDEN"
                    }
                    """
            ))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",schema = @Schema(example = """
                    {
                    	"message": "Unauthorized",
                    	"status": "UNAUTHORIZED"
                    }
                    """
            )))
    })
    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransferDTO dto) {
        this.service.createTransaction(dto);
        return ResponseEntity.ok().build();
    }

}

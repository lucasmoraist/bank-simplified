package com.lucasmoraist.bank_simplified.controller.user;

import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "V1")
public class FindByCpfController {

    @Autowired
    private UserService service;

    @Operation(summary = "Find user by CPF", description = "Find a user by its CPF")
    @Parameter(name = "cpf", description = "User's CPF", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                    	"message": "User not found",
                    	"status": "NOT_FOUND"
                    }
                    """)))
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<User> findByCpf(@PathVariable String cpf) {
        User user = this.service.findByCpf(cpf);
        return ResponseEntity.ok().body(user);
    }

}

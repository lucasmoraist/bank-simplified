package com.lucasmoraist.bank_simplified.controller.user;

import com.lucasmoraist.bank_simplified.dto.UserDTO;
import com.lucasmoraist.bank_simplified.model.User;
import com.lucasmoraist.bank_simplified.service.UserService;
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

@RestController
@RequestMapping("/v1/user")
@Tag(name = "V2")
@Slf4j
public class CreateUserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Create a new user", description = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "CPF already registered", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                    	"message": "CPF already registered",
                    	"status": "BAD_REQUEST"
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Email already registered", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                    	"message": "Email already registered",
                    	"status": "BAD_REQUEST"
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid password", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                    	"message": "Invalid password. Password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character.",
                    	"status": "BAD_REQUEST"
                    }
                    """))),
    })
    @PostMapping
    public ResponseEntity<User> savePerson(@Valid @RequestBody UserDTO dto) {
        log.info("Creating user with data: {}", dto);
        User user = this.service.save(dto);
        log.info("User created successfully");
        return ResponseEntity.ok().body(user);
    }

}

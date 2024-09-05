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
@RequestMapping("/v2/user")
@Tag(name = "V2")
public class FindByIdController {

    @Autowired
    private UserService service;

    @Operation(summary = "Find user by id", description = "Find a user by its id")
    @Parameter(name = "id", description = "User's id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                    {
                    	"message": "User not found",
                    	"status": "NOT_FOUND"
                    }
                    """)))
    })
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = this.service.findById(id);
        return ResponseEntity.ok().body(user);
    }

}

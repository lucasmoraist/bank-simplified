package com.lucasmoraist.bank_simplified.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents the response of the authorization endpoint.
 *
 * @author lucasmoraist
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationResponse {
    private String status;
    private AuthorizationData data;

    @Getter
    public static class AuthorizationData {
        private boolean authorization;

    }

}

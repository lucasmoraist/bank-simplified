package com.lucasmoraist.bank_simplified.model;

import lombok.*;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorizationData {
        private boolean authorization;
    }

}

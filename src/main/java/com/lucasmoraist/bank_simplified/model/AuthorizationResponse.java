package com.lucasmoraist.bank_simplified.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

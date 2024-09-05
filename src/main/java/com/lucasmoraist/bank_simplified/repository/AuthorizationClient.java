package com.lucasmoraist.bank_simplified.repository;

import com.lucasmoraist.bank_simplified.model.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authorization", url = "https://util.devi.tools/api/v2")
public interface AuthorizationClient {

    @GetMapping("/authorize")
    AuthorizationResponse authorize();

}

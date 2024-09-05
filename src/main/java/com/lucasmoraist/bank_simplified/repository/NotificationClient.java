package com.lucasmoraist.bank_simplified.repository;

import com.lucasmoraist.bank_simplified.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "https://util.devi.tools/api/v1")
public interface NotificationClient {

    @PostMapping("/notify")
    void sendNotification(@RequestBody NotificationDTO dto);

}

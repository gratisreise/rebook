package com.example.rebookuserservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service")
public interface NotificationClient {
    @PostMapping("/api/notifications/me/settings/{userId}")
    void createAllSettings(@PathVariable String userId);
}

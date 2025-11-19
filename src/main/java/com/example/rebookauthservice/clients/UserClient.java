package com.example.rebookauthservice.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service")
public interface UserClient {

}

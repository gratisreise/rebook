package com.example.rebooknotificationservice.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="chat-service")
public interface ChatClient {

}

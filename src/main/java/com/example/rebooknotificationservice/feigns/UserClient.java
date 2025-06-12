package com.example.rebooknotificationservice.feigns;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service", url="http://localhost:9000/api/users")
public interface UserClient {
    @GetMapping("/alarms/books")
    List<String> findUserIdsByCategory(@RequestParam String category);

    @GetMapping("/alarms/trades")
    List<String> findUserIdsByMarkedBook(String bookId);
}

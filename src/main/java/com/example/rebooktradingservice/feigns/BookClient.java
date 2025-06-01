package com.example.rebooktradingservice.feigns;

import com.example.rebooktradingservice.model.feigns.RecommendatedBook;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="book-service", url="http://localhost:9001/api/books")
public interface BookClient {
    @GetMapping("/recommendations/{userId}")
    List<Long> getRecommendedBooks(@PathVariable String userId);
}

package com.example.rebookgateway.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ValueOperations<String, String> valueOperations;

    public void set(String key, String value){
        valueOperations.set(key, value);
    }

    public Optional<String> get(String key){
        return Optional.ofNullable(valueOperations.get(key));
    }
}

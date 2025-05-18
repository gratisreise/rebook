package com.example.rebookuserservice.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ValueOperations<String, String> valueOperations;
    private static final long REFRESH_EXPIRE_TIME = 7L;

    public void set(String key, String value){
        valueOperations.set(key, value,
            REFRESH_EXPIRE_TIME,
            TimeUnit.DAYS
        );
    }

    public Optional<String> get(String key){
        return Optional.ofNullable(valueOperations.get(key));
    }

}

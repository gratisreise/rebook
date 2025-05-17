package com.example.rebookuserservice.service;

import com.example.rebookuserservice.model.TokenResponse;
import com.example.rebookuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public TokenResponse login(String code) {

    }

}

package com.example.rebookauthservice.service;

import com.example.rebookauthservice.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

}

package com.example.rebookuserservice.service;

import com.example.rebookuserservice.exception.CMissingDataException;
import com.example.rebookuserservice.model.entity.Users;
import com.example.rebookuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {
    private final UserRepository userRepository;

    //단일 유저 정보 조회
    public Users getUser(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(CMissingDataException::new);
    }
}

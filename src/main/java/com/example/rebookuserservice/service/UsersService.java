package com.example.rebookuserservice.service;

import com.example.rebookuserservice.exception.CMissingDataException;
import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.entity.Users;
import com.example.rebookuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserReader userReader;

    //유저 정보 조회
    public UsersResponse getUser(String userId) {
        Users user = userReader.getUser(userId);
        return new UsersResponse(user);
    }

}

package com.example.rebookuserservice.service;

import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import com.example.rebookuserservice.model.entity.Users;
import com.example.rebookuserservice.repository.UserRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UserRepository userRepository;
    private final UserReader userReader;
    private final S3Service s3Service;
    private final KeycloakService keycloakService;

    //유저 정보 조회
    public UsersResponse getUser(String userId) {
        Users user = userReader.getUser(userId);
        return new UsersResponse(user);
    }

    @Transactional
    public void updateUser(String userId, UsersUpdateRequest request) throws IOException {
        Users user = userReader.getUser(userId);

        String imageUrl = s3Service.upload(request.getMultipartFile());
        log.info("Image url: {}", imageUrl);

        Users updatedUser = user.update(request, imageUrl);
        log.info("User updated: {}", updatedUser);
    }
}

package com.example.rebookuserservice.service;

import com.example.rebookuserservice.exception.CDuplicatedDataException;
import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import com.example.rebookuserservice.model.entity.Users;
import com.example.rebookuserservice.repository.UserRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        if(request.getProfileImage() != null) {
            String imageUrl = s3Service.upload(request.getProfileImage());
            user.setProfileImage(imageUrl);
            log.info("Image url: {}", imageUrl);
        }

        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByNickname(request.getNickname())){
            throw new CDuplicatedDataException("이메일이나 닉네임이 중복됩니다.");
        }

        Users updatedUser = user.update(request);
        log.info("User updated: {}", updatedUser);
    }


    public void deleteUser(String id) {
        keycloakService.deleteUser(id);
        userRepository.deleteById(id);
    }
}

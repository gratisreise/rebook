package com.example.rebookuserservice.service;

import com.example.rebookuserservice.exception.CDuplicatedDataException;
import com.example.rebookuserservice.exception.CInvalidDataException;
import com.example.rebookuserservice.model.CategoryResponse;
import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import com.example.rebookuserservice.model.entity.Users;
import com.example.rebookuserservice.repository.FavoriteCategoryRepository;
import com.example.rebookuserservice.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UserRepository userRepository;
    private final UserReader userReader;
    private final S3Service s3Service;
    private final KeycloakService keycloakService;
    private final FavoriteCategoryRepository  favoriteCategoryRepository;

    //유저 정보 조회
    @Transactional(readOnly = true)
    public UsersResponse getUser(String userId) {
        Users user = userReader.getUser(userId);
        return new UsersResponse(user);
    }

    @Transactional
    public void updateUser(String userId, UsersUpdateRequest request, MultipartFile file) throws IOException {
        if(!userRepository.existsById(userId)) {
            throw new CInvalidDataException("존재하지 않는 유저입니다.");
        }

        Users user = userReader.getUser(userId);
        String newEmail = request.getEmail();
        String newNickname= request.getNickname();

        if(file != null) {
            String imageUrl = s3Service.upload(file);
            user.setProfileImage(imageUrl);
            log.info("Image url: {}", imageUrl);
        }

        if(!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)){
            throw new CDuplicatedDataException("중복된 이메일이 있습니다.");
        }

        if(!user.getNickname().equals(newNickname) && userRepository.existsByNickname(newNickname)){
            throw new CDuplicatedDataException("중복된 닉네임이 있습니다.");
        }

        Users updatedUser = user.update(request);
        log.info("User updated: {}", updatedUser);
    }


    @Transactional
    public void deleteUser(String userId) {
        if(!userRepository.existsById(userId)) {
            throw new CInvalidDataException("존재하지 않는 유저입니다.");
        }
        keycloakService.deleteUser(userId);
        userRepository.deleteById(userId);
    }

    public void updatePassword(String userId, String password) {
        if(!userRepository.existsById(userId)) {
            throw new CInvalidDataException("존재하지 않는 유저입니다.");
        }
        keycloakService.updatePassword(userId, password);
    }

    public CategoryResponse getCategories(String userId) {
        List<String> categories = getFavoriteCategories(userId);
        return new CategoryResponse(categories);
    }


    public List<String> getRecommendedCategories(String userId) {
        return getFavoriteCategories(userId);
    }

    private List<String> getFavoriteCategories(String userId) {
        return favoriteCategoryRepository
            .findByFavoriteCategoryIdUserId(userId)
            .stream()
            .map(f -> f.getFavoriteCategoryId().getCategory())
            .toList();
    }


    public UsersResponse getUserOther(String userId) {
        Users user = userReader.getUser(userId);
        return new UsersResponse(user);
    }
}

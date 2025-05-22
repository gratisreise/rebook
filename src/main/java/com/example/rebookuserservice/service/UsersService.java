package com.example.rebookuserservice.service;

import com.example.rebookuserservice.exception.CDuplicatedDataException;
import com.example.rebookuserservice.exception.CInvalidDataException;
import com.example.rebookuserservice.model.BookInfo;
import com.example.rebookuserservice.model.CategoryResponse;
import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import com.example.rebookuserservice.model.entity.UserBook;
import com.example.rebookuserservice.model.entity.UserTrading;
import com.example.rebookuserservice.model.entity.Users;
import com.example.rebookuserservice.repository.FavoriteCategoryRepository;
import com.example.rebookuserservice.repository.UserBookRepository;
import com.example.rebookuserservice.repository.UserRepository;
import com.example.rebookuserservice.repository.UserTradingRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final FavoriteCategoryRepository  favoriteCategoryRepository;
    private final UserBookRepository userBookRepository;
    private final UserTradingRepository userTradingRepository;

    //유저 정보 조회
    @Transactional(readOnly = true)
    public UsersResponse getUser(String userId) {
        Users user = userReader.getUser(userId);
        return new UsersResponse(user);
    }

    @Transactional
    public void updateUser(String userId, UsersUpdateRequest request) throws IOException {
        if(!userRepository.existsById(userId)) {
            throw new CInvalidDataException("존재하지 않는 유저입니다.");
        }
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
        List<String> categories = favoriteCategoryRepository
            .findByFavoriteCategoryIdUserId(userId)
            .stream()
            .map(f -> f.getFavoriteCategoryId().getCategory())
            .toList();
        return new CategoryResponse(categories);
    }

    public Page<UserBook> getMarkBooks(String userId, Pageable pageable) {
        //id로 도서 id 조회 페이지 네이션
        return userBookRepository.findAllUserBookIdUserId(userId, pageable);

        //도서 서비스에서 도서목록조회

        //도서 목록을 page에 넣고 보내기
    }

}

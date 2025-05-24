package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.common.CommonResult;
import com.example.rebookuserservice.common.ResponseService;
import com.example.rebookuserservice.common.SingleResult;
import com.example.rebookuserservice.model.CategoryResponse;
import com.example.rebookuserservice.model.PasswordUpdateRequest;
import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import com.example.rebookuserservice.model.entity.UserBook;
import com.example.rebookuserservice.model.entity.UserTrading;
import com.example.rebookuserservice.service.UsersService;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public SingleResult<UsersResponse> getUser(@RequestHeader("X-User-Id")String userId) {
        return ResponseService.getSingleResult(usersService.getUser(userId));
    }

    @PutMapping
    public CommonResult updateUser(
        @RequestHeader("X-User-Id")String userId,
        @Valid @ModelAttribute UsersUpdateRequest request
    ) throws IOException {
        log.info("update user {}", request.toString());
        usersService.updateUser(userId, request);
        return ResponseService.getSuccessResult();
    }

    @DeleteMapping
    public CommonResult deleteUser(@RequestHeader("X-User-Id")String userId){
        usersService.deleteUser(userId);
        return ResponseService.getSuccessResult();
    }

    @PatchMapping("/me")
    public CommonResult updatePassword(
        @RequestHeader("X-User-Id")String userId,
        @Valid @RequestBody PasswordUpdateRequest request
    ){
        log.info("update password {}", request.getPassword());
        usersService.updatePassword(userId, request.getPassword());
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/categories")
    public SingleResult<CategoryResponse> getCategories(@RequestHeader("X-User-Id")String userId) {
        return ResponseService.getSingleResult(usersService.getCategories(userId));
    }

    //짬한 도서목록 조회
    @GetMapping("/books")
    public SingleResult<Page<UserBook>> getMarkBooks(@RequestHeader("X-User-Id")String userId,  Pageable pageable){
        return ResponseService.getSingleResult(usersService.getMarkBooks(userId, pageable));
    }

    //찜한 거래목록 조회
    @GetMapping("/tradings")
    public SingleResult<Page<UserTrading>> getTradings(
        @RequestHeader("X-User-Id")String userId,  Pageable pageable){
        return ResponseService.getSingleResult(usersService.getMarkTradings(userId, pageable));
    }
}

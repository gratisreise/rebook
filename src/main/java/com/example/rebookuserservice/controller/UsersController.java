package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.common.CommonResult;
import com.example.rebookuserservice.common.ResponseService;
import com.example.rebookuserservice.common.SingleResult;
import com.example.rebookuserservice.model.CategoryResponse;
import com.example.rebookuserservice.model.PasswordUpdateRequest;
import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import com.example.rebookuserservice.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "유저API")
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    @Operation(summary = "유저조회")
    public SingleResult<UsersResponse> getUser(@RequestHeader("X-User-Id")String userId) {
        return ResponseService.getSingleResult(usersService.getUser(userId));
    }

    @PutMapping
    @Operation(summary = "유저수정")
    public CommonResult updateUser(
        @RequestHeader("X-User-Id")String userId,
        @Valid @ModelAttribute UsersUpdateRequest request
    ) throws IOException {
        log.info("update user {}", request.toString());
        usersService.updateUser(userId, request);
        return ResponseService.getSuccessResult();
    }

    @DeleteMapping
    @Operation(summary = "유저삭제")
    public CommonResult deleteUser(@RequestHeader("X-User-Id")String userId){
        usersService.deleteUser(userId);
        return ResponseService.getSuccessResult();
    }

    @PatchMapping("/me")
    @Operation(summary = "비밀번호수정")
    public CommonResult updatePassword(
        @RequestHeader("X-User-Id")String userId,
        @Valid @RequestBody PasswordUpdateRequest request
    ){
        log.info("update password {}", request.getPassword());
        usersService.updatePassword(userId, request.getPassword());
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/categories")
    @Operation(summary = "선호카테고리목록조회")
    public SingleResult<CategoryResponse> getCategories(@RequestHeader("X-User-Id")String userId) {
        return ResponseService.getSingleResult(usersService.getCategories(userId));
    }

    @GetMapping("/categories/recommendations/{userId}")
    public List<String> getUserBooks(@PathVariable String userId){
        return usersService.getRecommendedCategories(userId);
    }
}

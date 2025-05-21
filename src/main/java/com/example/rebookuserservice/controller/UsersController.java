package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.common.CommonResult;
import com.example.rebookuserservice.common.ResponseService;
import com.example.rebookuserservice.common.SingleResult;
import com.example.rebookuserservice.model.UsersResponse;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import com.example.rebookuserservice.service.UsersService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersService;

    //@RequestParam("id") String id

    @GetMapping
    public SingleResult<UsersResponse> getUser(@RequestHeader("X-User-Id")String userId) {
        return ResponseService.getSingleResult(usersService.getUser(userId));
    }

    @PutMapping
    public CommonResult updateUser(
        @RequestParam("id") String id,
        @Valid @ModelAttribute UsersUpdateRequest request
    ) throws IOException {
        log.info("update user {}", request.toString());
        usersService.updateUser(id, request);
        return ResponseService.getSuccessResult();
    }
}

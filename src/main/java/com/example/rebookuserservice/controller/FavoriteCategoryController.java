package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.common.CommonResult;
import com.example.rebookuserservice.common.ResponseService;
import com.example.rebookuserservice.model.CategoryRequest;
import com.example.rebookuserservice.model.entity.FavoriteCategory;
import com.example.rebookuserservice.model.entity.compositekey.FavoriteCategoryId;
import com.example.rebookuserservice.repository.FavoriteCategoryRepository;
import com.example.rebookuserservice.service.FavoriteCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FavoriteCategoryController {

    private final FavoriteCategoryService favoriteCategoryService;

    @PostMapping("/categories")
    public CommonResult postFavoriteCategory(@RequestParam String userId,
        @RequestBody CategoryRequest request) {
        favoriteCategoryService.postCategories(userId, request);
        return ResponseService.getSuccessResult();
    }
}

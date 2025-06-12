package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.model.feigns.AuthorsRequest;
import com.example.rebookuserservice.service.FavoriteCategoryReader;
import com.example.rebookuserservice.service.UserReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ReaderController {
    private final UserReader userReader;
    private final FavoriteCategoryReader favoriteCategoryReader;

    @PostMapping("/authors")
    public List<String> getAuthors(@RequestBody AuthorsRequest request) {
        return userReader.getAuthors(request);
    }

    @GetMapping("/alarms/books")
    public  List<String> findUserIdsByCategory(@RequestParam String category) {
        return favoriteCategoryReader.findByCategory(category);
    }


}

package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.model.feigns.AuthorsRequest;
import com.example.rebookuserservice.service.UserReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReaderController {
    private final UserReader userReader;

    @PostMapping("/api/users/authors")
    public List<String> getAuthors(@RequestBody AuthorsRequest request) {
        return userReader.getAuthors(request);
    }


}

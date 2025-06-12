package com.example.rebookuserservice.service;

import com.example.rebookuserservice.repository.FavoriteCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCategoryReader {
    private final FavoriteCategoryRepository favoriteCategoryRepository;

    public List<String> findByCategory(String category){
        return favoriteCategoryRepository
            .findByFavoriteCategoryIdCategory(category)
            .stream()
            .map(data -> data.getFavoriteCategoryId().getCategory())
            .toList();
    }
}

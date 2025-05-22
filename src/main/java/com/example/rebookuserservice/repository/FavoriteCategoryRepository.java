package com.example.rebookuserservice.repository;

import com.example.rebookuserservice.model.entity.FavoriteCategory;
import com.example.rebookuserservice.model.entity.compositekey.FavoriteCategoryId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteCategoryRepository extends JpaRepository<FavoriteCategory, FavoriteCategoryId> {
    List<FavoriteCategory> findByFavoriteCategoryIdUserId(String userId);
    List<FavoriteCategory> findByFavoriteCategoryIdCategory(String category);
}

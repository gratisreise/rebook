package com.example.rebookuserservice.model.entity;

import com.example.rebookuserservice.model.entity.compositekey.FavoriteCategoryId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FavoriteCategory {
    @EmbeddedId
    private FavoriteCategoryId favoriteCategoryId;
}

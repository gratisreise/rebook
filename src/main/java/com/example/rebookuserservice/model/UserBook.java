package com.example.rebookuserservice.model;

import com.example.rebookuserservice.model.compositekey.UserBookId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserBook {
    @EmbeddedId
    private UserBookId userBookId;
}

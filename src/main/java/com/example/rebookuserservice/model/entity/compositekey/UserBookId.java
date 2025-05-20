package com.example.rebookuserservice.model.entity.compositekey;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserBookId {
    private String userId;
    private Long bookId;
}

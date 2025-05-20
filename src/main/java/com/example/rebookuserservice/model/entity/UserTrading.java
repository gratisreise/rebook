package com.example.rebookuserservice.model.entity;

import com.example.rebookuserservice.model.entity.compositekey.UserTradingId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserTrading {
    @EmbeddedId
    private UserTradingId userTradingId;
}

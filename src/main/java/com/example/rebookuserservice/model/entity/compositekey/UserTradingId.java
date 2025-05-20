package com.example.rebookuserservice.model.entity.compositekey;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserTradingId {
    private String userId;
    private Long tradingId;
}

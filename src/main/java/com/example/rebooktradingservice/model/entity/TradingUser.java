package com.example.rebooktradingservice.model.entity;

import com.example.rebooktradingservice.model.entity.compositekey.TradingUserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TradingUser {
    @EmbeddedId
    private TradingUserId tradingUserId;

    @MapsId("tradingId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trading_id")
    private Trading trading;
}

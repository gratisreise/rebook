package com.example.rebooktradingservice.service;

import com.example.rebooktradingservice.exception.CMissingDataException;
import com.example.rebooktradingservice.model.entity.Trading;
import com.example.rebooktradingservice.model.entity.TradingUser;
import com.example.rebooktradingservice.model.entity.compositekey.TradingUserId;
import com.example.rebooktradingservice.repository.TradingRepository;
import com.example.rebooktradingservice.repository.TradingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradingUserService {

    private final TradingUserRepository tradingUserRepository;
    private final TradingRepository tradingRepository;

    @Transactional
    public void tradingMark(String userId, Long tradingId) {
        if(!tradingRepository.existsById(tradingId)){
            throw new CMissingDataException("Trading not found");
        }
        TradingUserId tradingUserId = new TradingUserId(tradingId, userId);
        Trading trading = Trading.builder().id(tradingId).build();
        TradingUser tradingUser = new TradingUser(tradingUserId, trading);
        tradingUserRepository.save(tradingUser);
    }

}

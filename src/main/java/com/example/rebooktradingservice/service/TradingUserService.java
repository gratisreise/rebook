package com.example.rebooktradingservice.service;

import com.example.rebooktradingservice.exception.CMissingDataException;
import com.example.rebooktradingservice.model.TradingResponse;
import com.example.rebooktradingservice.model.entity.Trading;
import com.example.rebooktradingservice.model.entity.TradingUser;
import com.example.rebooktradingservice.model.entity.compositekey.TradingUserId;
import com.example.rebooktradingservice.repository.TradingRepository;
import com.example.rebooktradingservice.repository.TradingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradingUserService {

    private final TradingUserRepository tradingUserRepository;
    private final TradingRepository tradingRepository;


    @Transactional
    public void tradingMark(String userId, Long tradingId) {
        if(!tradingRepository.existsById(tradingId)){
            tradingRepository.deleteById(tradingId);
            return;
        }
        TradingUserId tradingUserId = new TradingUserId(tradingId, userId);
        Trading trading = tradingRepository.findById(tradingId)
            .orElseThrow(CMissingDataException::new);
        TradingUser tradingUser = new TradingUser(tradingUserId, trading);
        tradingUserRepository.save(tradingUser);
    }

    @Transactional
    public void tradingUnMark(String userId, Long tradingId) {
        if(!tradingRepository.existsById(tradingId)){
            throw new CMissingDataException("Trading not found");
        }
        TradingUserId tradingUserId = new TradingUserId(tradingId, userId);
        tradingUserRepository.deleteById(tradingUserId);
    }


    public Page<TradingResponse> getMarkedTradings(String userId, Pageable pageable) {
        Page<Trading> markedTradings = tradingUserRepository.findTradingByUserId(userId, pageable);
        return markedTradings.map(TradingResponse::new);
    }
}

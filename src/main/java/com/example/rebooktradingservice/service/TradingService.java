package com.example.rebooktradingservice.service;

import com.example.rebooktradingservice.common.PageResponse;
import com.example.rebooktradingservice.enums.State;
import com.example.rebooktradingservice.exception.CUnauthorizedException;
import com.example.rebooktradingservice.model.TradingRequest;
import com.example.rebooktradingservice.model.TradingResponse;
import com.example.rebooktradingservice.model.entity.Trading;
import com.example.rebooktradingservice.repository.TradingRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradingService {

    private final TradingRepository tradingRepository;
    private final TradingReader tradingReader;
    private final S3Service s3Service;

    @Transactional
    public void postTrading(TradingRequest request, String userId) throws IOException {
        String imageUrl =  s3Service.upload(request.getImage());
        Trading trading = new Trading(request, imageUrl, userId);
        tradingRepository.save(trading);
    }

    public TradingResponse getTrading(Long tradingId) {
        Trading trading = tradingReader.readTrading(tradingId);
        return new TradingResponse(trading);
    }

    @Transactional
    public void updateState(Long tradingId, State state, String userId) {
        Trading trading = tradingReader.readTrading(tradingId);
        if(!trading.getUserId().equals(userId)) {
            log.error("Unauthorized updateTrading Access");
            throw new CUnauthorizedException("Unauthorized user Access");
        }
        trading.setState(state);
    }

    @Transactional
    public void updateTrading(TradingRequest request, String userId, Long tradingId)
        throws IOException {
        Trading trading = tradingReader.readTrading(tradingId);
        if(!trading.getUserId().equals(userId)) {
            log.error("Unauthorized updateState Access");
            throw new CUnauthorizedException("Unauthorized user Access");
        }
        String imageUrl = s3Service.upload(request.getImage());
        trading.update(request, imageUrl, userId);
    }

    public PageResponse<TradingResponse> getTradings(String userId, Pageable pageable) {
        Page<Trading> tradings = tradingReader.readTradings(userId, pageable);
        Page<TradingResponse> responses = tradings.map(TradingResponse::new);
        return new PageResponse<>(responses);
    }
}

package com.example.rebooktradingservice.service;

import com.example.rebooktradingservice.model.TradingRequest;
import com.example.rebooktradingservice.model.entity.Trading;
import com.example.rebooktradingservice.repository.TradingRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradingService {

    private final TradingRepository tradingRepository;
    private final S3Service s3Service;


    public void postTrading(TradingRequest request, String userId) throws IOException {
        String imageUrl =  s3Service.upload(request.getImage());
        Trading trading = new Trading(request, imageUrl, userId);
        tradingRepository.save(trading);
    }

}

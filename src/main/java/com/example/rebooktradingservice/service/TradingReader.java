package com.example.rebooktradingservice.service;

import com.example.rebooktradingservice.exception.CMissingDataException;
import com.example.rebooktradingservice.model.entity.Trading;
import com.example.rebooktradingservice.repository.TradingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TradingReader {
    private final TradingRepository tradingRepository;

    public Trading readTrading(Long tradingId) {
        return tradingRepository.findById(tradingId)
            .orElseThrow(CMissingDataException::new);
    }

}

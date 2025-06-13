package com.example.rebooktradingservice.service;

import com.example.rebooktradingservice.exception.CMissingDataException;
import com.example.rebooktradingservice.model.entity.Trading;
import com.example.rebooktradingservice.repository.TradingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TradingReader {
    private final TradingRepository tradingRepository;

    public Trading findById(Long tradingId) {
        return tradingRepository.findById(tradingId)
            .orElseThrow(CMissingDataException::new);
    }

    public Page<Trading> readTradings(String userId, Pageable pageable) {
        return tradingRepository.findByUserId(userId, pageable);
    }

    public Page<Trading> getAllTradings(Long bookId, Pageable pageable) {
        return tradingRepository.findByBookId(bookId, pageable);
    }

    public Page<Trading> getRecommendations(List<Long> bookIds, Pageable pageable) {
        return tradingRepository.findByBookIdIn(bookIds, pageable);
    }

}

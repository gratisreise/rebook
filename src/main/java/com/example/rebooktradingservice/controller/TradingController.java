package com.example.rebooktradingservice.controller;

import com.example.rebooktradingservice.common.CommonResult;
import com.example.rebooktradingservice.common.PageResponse;
import com.example.rebooktradingservice.common.ResponseService;
import com.example.rebooktradingservice.common.SingleResult;
import com.example.rebooktradingservice.enums.State;
import com.example.rebooktradingservice.model.TradingRequest;
import com.example.rebooktradingservice.model.TradingResponse;
import com.example.rebooktradingservice.service.TradingReader;
import com.example.rebooktradingservice.service.TradingService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tradings")
public class TradingController {
    // @RequestHeader("X-User-Id") String userId
    // @RequestHeader("X-User-Id") String userId
    private final TradingService tradingService;
    private final TradingReader tradingReader;


    @PostMapping
    public CommonResult postTrading(@RequestHeader("X-User-Id") String userId, @ModelAttribute TradingRequest request)
        throws IOException {
        tradingService.postTrading(request, userId);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/{tradingId}")
    public SingleResult<TradingResponse> getTrading(@PathVariable Long tradingId){
        return ResponseService.getSingleResult(tradingService.getTrading(tradingId));
    }

    @PatchMapping("/{tradingId}")
    public CommonResult updateState(
        @PathVariable Long tradingId,
        @RequestParam State state,
        @RequestHeader("X-User-Id") String userId
    ){
        tradingService.updateState(tradingId, state, userId);
        return ResponseService.getSuccessResult();
    }

    @PutMapping("/{tradingId}")
    public CommonResult updateTrading(
        @PathVariable Long tradingId, @RequestHeader("X-User-Id") String userId,
        @ModelAttribute TradingRequest request
    ) throws IOException {
        tradingService.updateTrading(request, userId, tradingId);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/me")
    public SingleResult<PageResponse<TradingResponse>> getTradings(
        @RequestHeader("X-User-Id") String userId, @PageableDefault Pageable pageable
    ){
        return ResponseService.getSingleResult(tradingService.getTradings(userId, pageable));
    }

    @DeleteMapping("/{tradingId}")
    public CommonResult deleteTrading(@PathVariable Long tradingId, @RequestHeader("X-User-Id") String userId){
        tradingService.deleteTrading(tradingId, userId);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/books/{bookId}")
    public SingleResult<PageResponse<TradingResponse>> getAllTradings(
        @PathVariable Long bookId, @PageableDefault Pageable pageable){
        return ResponseService.getSingleResult(tradingService.getAllTradings(bookId, pageable));
    }

    @GetMapping("/recommendations")
    public SingleResult<PageResponse<TradingResponse>> getRecommendations(
        @RequestHeader("X-User-Id") String userId, @PageableDefault Pageable pageable
    ){
        return ResponseService.getSingleResult(tradingService.getRecommendations(userId, pageable));
    }
}

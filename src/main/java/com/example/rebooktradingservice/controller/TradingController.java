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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tradings")
public class TradingController {
    // @RequestHeader("X-User-Id") String userId
    // @RequestParam String userId
    private final TradingService tradingService;
    private final TradingReader tradingReader;


    @PostMapping
    public CommonResult postTrading(@RequestParam String userId, @ModelAttribute TradingRequest request)
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
        @RequestParam String userId
    ){
        tradingService.updateState(tradingId, state, userId);
        return ResponseService.getSuccessResult();
    }

    @PutMapping("/{tradingId}")
    public CommonResult updateTrading(
        @PathVariable Long tradingId, @RequestParam String userId,
        @ModelAttribute TradingRequest request
    ) throws IOException {
        tradingService.updateTrading(request, userId, tradingId);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/me")
    public SingleResult<PageResponse<TradingResponse>> getTradings(
        @RequestParam String userId, @PageableDefault Pageable pageable
    ){
        return ResponseService.getSingleResult(tradingService.getTradings(userId, pageable));
    }

    @DeleteMapping("/{tradingId}")
    public CommonResult deleteTrading(@PathVariable Long tradingId, @RequestParam String userId){
        tradingService.deleteTrading(tradingId, userId);
        return ResponseService.getSuccessResult();
    }

}

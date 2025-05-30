package com.example.rebooktradingservice.controller;

import com.example.rebooktradingservice.common.CommonResult;
import com.example.rebooktradingservice.common.ResponseService;
import com.example.rebooktradingservice.common.SingleResult;
import com.example.rebooktradingservice.enums.State;
import com.example.rebooktradingservice.model.TradingRequest;
import com.example.rebooktradingservice.model.TradingResponse;
import com.example.rebooktradingservice.service.TradingService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public CommonResult updateState(@PathVariable Long tradingId, @RequestParam State state){
        tradingService.updateState(tradingId, state);
        return ResponseService.getSuccessResult();
    }



}

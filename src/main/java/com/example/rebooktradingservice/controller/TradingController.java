package com.example.rebooktradingservice.controller;

import com.example.rebooktradingservice.common.CommonResult;
import com.example.rebooktradingservice.common.ResponseService;
import com.example.rebooktradingservice.model.TradingRequest;
import com.example.rebooktradingservice.service.TradingService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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



}

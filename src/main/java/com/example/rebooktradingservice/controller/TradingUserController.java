package com.example.rebooktradingservice.controller;

import com.example.rebooktradingservice.common.CommonResult;
import com.example.rebooktradingservice.common.ResponseService;
import com.example.rebooktradingservice.service.TradingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tradings")
public class TradingUserController {
    // @RequestHeader("X-User-Id") String userId
    // @RequestHeader("X-User-Id") String userId

    private final TradingUserService tradingUserService;

    @PostMapping("/{tradingId}/marks")
    public CommonResult tradingMark(@RequestHeader("X-User-Id") String userId, @PathVariable Long tradingId) {
        tradingUserService.tradingMark(userId, tradingId);
        return ResponseService.getSuccessResult();
    }
}

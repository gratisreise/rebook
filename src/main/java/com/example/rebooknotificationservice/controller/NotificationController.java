package com.example.rebooknotificationservice.controller;

import com.example.rebooknotificationservice.common.CommonResult;
import com.example.rebooknotificationservice.common.PageResponse;
import com.example.rebooknotificationservice.common.ResponseService;
import com.example.rebooknotificationservice.common.SingleResult;
import com.example.rebooknotificationservice.model.NotificationResponse;
import com.example.rebooknotificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

//    @PostMapping
//    public CommonResult createNotification(@Valid @RequestBody NotificationRequest request) {
//        notificationService.createNotification(request);
//        return ResponseService.getSuccessResult();
//    }

    @GetMapping("/me")
    @Operation(summary = "내 알림 목록 조회")
    public SingleResult<PageResponse<NotificationResponse>> getNotifications(
        @RequestParam String userId, @PageableDefault Pageable pageable) {
        return ResponseService.getSingleResult(notificationService.getNotifications(userId, pageable));
    }

    @PatchMapping("/{notificationId}")
    @Operation(summary = "알림 읽음")
    public CommonResult readNotification(@PathVariable Long notificationId) {
        notificationService.readNotification(notificationId);
        return ResponseService.getSuccessResult();
    }

    @GetMapping("/me/numbers")
    @Operation(summary = "읽지 않은 알림 갯수")
    public SingleResult<Long> getNotReadNumbers(@RequestParam String userId) {
        return ResponseService.getSingleResult(notificationService.getNotReadNumbers(userId)) ;
    }
}

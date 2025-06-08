package com.example.rebooknotificationservice.controller;

import com.example.rebooknotificationservice.common.CommonResult;
import com.example.rebooknotificationservice.common.PageResponse;
import com.example.rebooknotificationservice.common.ResponseService;
import com.example.rebooknotificationservice.common.SingleResult;
import com.example.rebooknotificationservice.model.NotificationRequest;
import com.example.rebooknotificationservice.model.NotificationResponse;
import com.example.rebooknotificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public CommonResult createNotification(@Valid @RequestBody NotificationRequest request) {
        notificationService.createNotification(request);
        return ResponseService.getSuccessResult();
    }

    @GetMapping
    public SingleResult<PageResponse<NotificationResponse>> getNotifications(
        @RequestParam String userId, @PageableDefault Pageable pageable) {
        return ResponseService.getSingleResult(notificationService.getNotifications(userId, pageable));
    }
}

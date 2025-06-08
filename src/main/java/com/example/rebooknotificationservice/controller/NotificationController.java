package com.example.rebooknotificationservice.controller;

import com.example.rebooknotificationservice.common.CommonResult;
import com.example.rebooknotificationservice.common.ResponseService;
import com.example.rebooknotificationservice.model.NotificationRequest;
import com.example.rebooknotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public CommonResult createNotification(@RequestBody NotificationRequest request) {
        notificationService.createNotification(request);
        return ResponseService.getSuccessResult();
    }
}

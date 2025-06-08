package com.example.rebooknotificationservice.controller;

import com.example.rebooknotificationservice.service.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationSettingController {
    private final NotificationSettingService notificationSettingService;

}

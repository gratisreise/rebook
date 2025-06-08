package com.example.rebooknotificationservice.controller;

import com.example.rebooknotificationservice.common.ListResult;
import com.example.rebooknotificationservice.common.ResponseService;
import com.example.rebooknotificationservice.common.SingleResult;
import com.example.rebooknotificationservice.model.entity.NotificationSetting;
import com.example.rebooknotificationservice.service.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationSettingController {
    private final NotificationSettingService notificationSettingService;

    @GetMapping("/me/settings")
    public ListResult<NotificationSetting> getNotificationSettings(@RequestParam String userId) {
        return ResponseService.getListResult(notificationSettingService.getAllNotificationSettings(userId));
    }
}

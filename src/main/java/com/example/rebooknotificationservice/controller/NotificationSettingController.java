package com.example.rebooknotificationservice.controller;

import com.example.rebooknotificationservice.common.CommonResult;
import com.example.rebooknotificationservice.common.ListResult;
import com.example.rebooknotificationservice.common.ResponseService;
import com.example.rebooknotificationservice.common.SingleResult;
import com.example.rebooknotificationservice.enums.Type;
import com.example.rebooknotificationservice.model.NotificationSettingResponse;
import com.example.rebooknotificationservice.model.entity.NotificationSetting;
import com.example.rebooknotificationservice.service.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ListResult<NotificationSettingResponse> getNotificationSettings(@RequestParam String userId) {
        return ResponseService.getListResult(notificationSettingService.getAllNotificationSettings(userId));

    }
    @PatchMapping("/settings")
    public CommonResult toggleNotificationSetting(@RequestParam Type type, @RequestParam String userId){
        notificationSettingService.toggleNotificationSetting(type, userId);
        return ResponseService.getSuccessResult();
    }
}

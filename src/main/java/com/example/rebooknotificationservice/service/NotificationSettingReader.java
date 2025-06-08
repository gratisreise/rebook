package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.model.entity.NotificationSetting;
import com.example.rebooknotificationservice.repository.NotificationSettingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSettingReader {
    private final NotificationSettingRepository notificationSettingRepository;

    public List<NotificationSetting> getAllNotificationSettings(String userId) {
        return notificationSettingRepository.findById_UserId(userId);
    }

}

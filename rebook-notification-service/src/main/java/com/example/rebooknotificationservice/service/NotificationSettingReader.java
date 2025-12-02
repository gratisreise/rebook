package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.enums.Type;
import com.example.rebooknotificationservice.exception.CMissingDataException;
import com.example.rebooknotificationservice.model.entity.NotificationSetting;
import com.example.rebooknotificationservice.model.entity.compositekey.NotificationSettingId;
import com.example.rebooknotificationservice.repository.NotificationSettingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSettingReader {
    private final NotificationSettingRepository notificationSettingRepository;

    public List<NotificationSetting> getAllNotificationSettings(String userId) {
        return notificationSettingRepository.findByNotificationSettingIdUserId(userId);
    }

    public NotificationSetting findById(Type type, String userId) {
        NotificationSettingId settingId = new NotificationSettingId(userId, type);
        return notificationSettingRepository.findById(settingId)
            .orElseThrow(CMissingDataException::new);
    }
}

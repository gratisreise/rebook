package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.repository.NotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSettingService {
    private final NotificationSettingRepository notificationSettingRepository;


}

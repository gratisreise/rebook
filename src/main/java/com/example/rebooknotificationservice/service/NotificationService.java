package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.model.NotificationRequest;
import com.example.rebooknotificationservice.model.entity.Notification;
import com.example.rebooknotificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    //알림생성
    public void createNotification(NotificationRequest request) {
        Notification notification = new Notification(request);

    }

}

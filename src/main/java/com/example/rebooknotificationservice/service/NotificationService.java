package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.common.PageResponse;
import com.example.rebooknotificationservice.model.NotificationRequest;
import com.example.rebooknotificationservice.model.NotificationResponse;
import com.example.rebooknotificationservice.model.entity.Notification;
import com.example.rebooknotificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationReader notificationReader;
    //알림생성
    @Transactional
    public void createNotification(NotificationRequest request) {
        Notification notification = new Notification(request);
        notificationRepository.save(notification);
    }


    public PageResponse<NotificationResponse> getNotifications(String userId, Pageable pageable) {
        Page<Notification> notifications = notificationReader.getNotifications(userId, pageable);
        Page<NotificationResponse> responses = notifications.map(NotificationResponse::new);
        return new PageResponse<>(responses);
    }
}

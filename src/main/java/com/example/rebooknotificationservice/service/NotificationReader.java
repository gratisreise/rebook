package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.model.entity.Notification;
import com.example.rebooknotificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationReader {
    private final NotificationRepository notificationRepository;

    public Page<Notification> getNotifications(String userId, Pageable pageable){
        return notificationRepository.findByUserId(userId, pageable);
    }
}

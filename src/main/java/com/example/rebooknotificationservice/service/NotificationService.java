package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.common.PageResponse;
import com.example.rebooknotificationservice.exception.CMissingDataException;
import com.example.rebooknotificationservice.feigns.ChatClient;
import com.example.rebooknotificationservice.feigns.UserClient;
import com.example.rebooknotificationservice.model.NotificationMessage;
import com.example.rebooknotificationservice.model.NotificationResponse;
import com.example.rebooknotificationservice.model.entity.Notification;
import com.example.rebooknotificationservice.repository.NotificationRepository;
import java.util.List;
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
    private final NotificationSettingService notificationSettingService;
    private final UserClient userClient;
    private final ChatClient chatClient;

    //알림생성
    @Transactional
    public List<String> createNotification(NotificationMessage message) throws CMissingDataException {
        //유저목록가져오기
        return switch(message.getType()){
            case "book" -> sendBookAlaram(message);
            case "chat" -> sendChatAlaram(message);
            case "trade" ->sendTradeAlaram(message);
            case "payment" -> sendPaymentAlarm(message);
            default -> throw new CMissingDataException();
        };
    }

    public PageResponse<NotificationResponse> getNotifications(String userId, Pageable pageable) {
        Page<Notification> notifications = notificationReader.getNotifications(userId, pageable);
        Page<NotificationResponse> responses = notifications.map(NotificationResponse::new);
        return new PageResponse<>(responses);
    }

    //알림읽음
    @Transactional
    public void readNotification(Long notificationId) {
        Notification notification = notificationReader.findById(notificationId);
        notification.setRead(true);
    }

    public Long getNotReadNumbers(String userId) {
        return notificationReader.getNotReadNumbers(userId);
    }



    public List<String> sendBookAlaram(NotificationMessage message) {
        List<String> userIds = userClient.findUserIdsByCategory(message.getRelatedInfo());
        userIds.forEach(userId -> {
            Notification notification = new Notification(message, userId);
            notificationSettingService.createNotificationSetting(notification);
            notificationRepository.save(notification);
        });
        return userIds;
    }

    public List<String> sendChatAlaram(NotificationMessage message) {
        Notification notification = new Notification(message, message.getUserId());
        notificationRepository.save(notification);
        return List.of(notification.getUserId());
    }

    public List<String> sendTradeAlaram(NotificationMessage message) {

        return null;
    }

    public List<String> sendPaymentAlarm(NotificationMessage message) {
        return null;
    }
}

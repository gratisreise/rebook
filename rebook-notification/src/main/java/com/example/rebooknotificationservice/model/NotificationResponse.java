package com.example.rebooknotificationservice.model;

import com.example.rebooknotificationservice.enums.Type;
import com.example.rebooknotificationservice.model.entity.Notification;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    private Long notificationId;
    private String relatedId;
    private String message;
    private Type type;
    private LocalDateTime createdAt;


    public NotificationResponse(Notification notification) {
        this.notificationId = notification.getId();
        this.relatedId = notification.getRelatedId();
        this.message = notification.getMessage();
        this.type = notification.getType();
        this.createdAt = notification.getCreatedAt();
    }
}

package com.example.rebooknotificationservice.model;

import com.example.rebooknotificationservice.enums.Type;
import com.example.rebooknotificationservice.model.entity.Notification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    private String relatedId;
    private String message;
    private Type type;

    public NotificationResponse(Notification notification) {
        this.relatedId = notification.getRelatedId();
        this.message = notification.getMessage();
        this.type = notification.getType();
    }
}

package com.example.rebooknotificationservice.model;

import com.example.rebooknotificationservice.enums.Type;
import com.example.rebooknotificationservice.model.entity.NotificationSetting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSettingResponse {
    private String userId;
    private Type type;
    private boolean sendable;

    public NotificationSettingResponse(NotificationSetting notificationSetting) {
        this.userId = notificationSetting.getNotification().getUserId();
        this.type = notificationSetting.getNotification().getType();
        this.sendable = notificationSetting.isSendable();
    }
}

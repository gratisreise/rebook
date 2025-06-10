package com.example.rebooknotificationservice.model.entity.compositekey;

import com.example.rebooknotificationservice.enums.Type;
import com.example.rebooknotificationservice.model.entity.Notification;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSettingId {
    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Type type;

    public NotificationSettingId(Notification notification) {
        this.userId = notification.getUserId();
        this.type = notification.getType();
    }
}

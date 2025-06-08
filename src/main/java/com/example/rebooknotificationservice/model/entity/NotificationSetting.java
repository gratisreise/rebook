package com.example.rebooknotificationservice.model.entity;

import com.example.rebooknotificationservice.model.entity.compositekey.NotificationSettingId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSetting {
    @EmbeddedId
    private NotificationSettingId notificationSettingId;

    @Column(nullable = false)
    private boolean sendable;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private Notification notification;
}

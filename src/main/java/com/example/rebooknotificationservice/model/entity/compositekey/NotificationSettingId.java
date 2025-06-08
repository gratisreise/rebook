package com.example.rebooknotificationservice.model.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class NotificationSettingId {
    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 20)
    private String type;
}

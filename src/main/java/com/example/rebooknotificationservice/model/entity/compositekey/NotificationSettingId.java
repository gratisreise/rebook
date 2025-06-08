package com.example.rebooknotificationservice.model.entity.compositekey;

import com.example.rebooknotificationservice.enums.Type;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class NotificationSettingId {
    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Type type;
}

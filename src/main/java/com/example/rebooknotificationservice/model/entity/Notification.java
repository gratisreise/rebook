package com.example.rebooknotificationservice.model.entity;

import com.example.rebooknotificationservice.enums.Type;
import com.example.rebooknotificationservice.model.NotificationMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String content;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private boolean read;

    @Column(nullable = false, length = 50)
    private String relatedId; // 타입이 payment이면 userId

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public Notification(NotificationMessage request) {
        this.userId = request.getUserId();
        this.type = Type.valueOf(request.getType());
        this.content = request.getContent();
        this.relatedId = request.getRelatedId();
        this.read = false;
    }
}
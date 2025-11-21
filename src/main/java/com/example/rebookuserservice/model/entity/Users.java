package com.example.rebookuserservice.model.entity;

import com.example.rebookuserservice.enums.Provider;
import com.example.rebookuserservice.model.UserInfo;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
public class Users {
    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 30)
    private String email;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false, length = 300)
    private String profileImage;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Users(UserInfo userInfo) {
        this.id = userInfo.getUserId();
        this.email = userInfo.getEmail();
    }

    public Users update(UsersUpdateRequest request) {
        this.nickname = request.getNickname();
        this.email = request.getEmail();
        return this;
    }
}

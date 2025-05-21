package com.example.rebookuserservice.model.entity;

import com.example.rebookuserservice.enums.Role;
import com.example.rebookuserservice.model.UserInfo;
import com.example.rebookuserservice.model.UsersUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Users {
    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false, length = 300)
    private String profileImage;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Users(UserInfo userInfo) {
        this.id = userInfo.getUserId();
        this.username = userInfo.getUsername();
        this.email = userInfo.getEmail();
        this.role = userInfo.getRole().equals("admin") ? Role.ADMIN : Role.USER;
    }

    public Users update(UsersUpdateRequest request) {
        this.nickname = request.getNickname();
        this.email = request.getEmail();
        return this;
    }
}

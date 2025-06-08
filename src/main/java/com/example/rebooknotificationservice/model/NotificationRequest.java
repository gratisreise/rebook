package com.example.rebooknotificationservice.model;

import com.example.rebooknotificationservice.enums.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private Type type;

    private boolean read;

    @NotBlank
    private String relatedId;
}

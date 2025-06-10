package com.example.rebooknotificationservice.model;

import com.example.rebooknotificationservice.enums.Type;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String content;

    @NotBlank
    private String type;

    @NotBlank
    private String relatedId;
}

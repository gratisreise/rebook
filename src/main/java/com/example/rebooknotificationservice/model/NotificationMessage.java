package com.example.rebooknotificationservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMessage {
    @NotBlank
    private String userId;

    @NotBlank
    private String content;

    @NotBlank
    private String type;

    @NotBlank
    private String relatedId;
}

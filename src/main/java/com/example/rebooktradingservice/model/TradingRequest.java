package com.example.rebooktradingservice.model;

import com.example.rebooktradingservice.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TradingRequest {
    private Long bookId;
    private String title;
    private String rating;
    private Integer price;
    private State state;
    private MultipartFile image;
}
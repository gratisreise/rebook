package com.example.rebooktradingservice.model;

import com.example.rebooktradingservice.enums.State;
import com.example.rebooktradingservice.model.entity.Trading;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradingResponse {
    private Long tradingId;
    private Long bookId;
    private String userId;
    private String title;
    private String content;
    private String rating; // 최상, 상, 중, 하
    private Integer price;
    private State state; //
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isMarked;

    public TradingResponse(Trading trading) {
        this.tradingId = trading.getId();
        this.bookId = trading.getBookId();
        this.userId = trading.getUserId();
        this.title = trading.getTitle();
        this.content = trading.getContent();
        this.rating = trading.getRating();
        this.price = trading.getPrice();
        this.state = trading.getState();
        this.imageUrl = trading.getImageUrl();
        this.createdAt = trading.getCreatedAt();
        this.updatedAt = trading.getUpdatedAt();
    }
}

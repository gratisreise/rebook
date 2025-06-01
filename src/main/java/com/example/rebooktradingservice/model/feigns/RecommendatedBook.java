package com.example.rebooktradingservice.model.feigns;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendatedBook {
    private List<Long> bookId;
}

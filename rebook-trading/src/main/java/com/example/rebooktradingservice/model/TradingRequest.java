package com.example.rebooktradingservice.model;

import com.example.rebooktradingservice.enums.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TradingRequest {
    @NotBlank
    private Long bookId;

    @NotBlank
    @Length(min = 3, max = 100)
    private String title;

    @NotBlank
    @Length(min = 3, max = 800)
    private String content;

    @NotBlank
    @Length(min = 1, max = 5)
    private String rating;


    private int price;

    @NotNull
    private State state;

}
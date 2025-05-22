package com.example.rebookuserservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInfo {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String description;
}

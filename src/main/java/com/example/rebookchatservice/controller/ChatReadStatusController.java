package com.example.rebookchatservice.controller;

import com.example.rebookchatservice.service.ChatReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatReadStatusController {
    // @RquestParam
    // @RequestHeader("X-User-Id) String myId
    private final ChatReadStatusService chatReadStatusService;
}

package com.example.rebookchatservice.controller;

import com.example.rebookchatservice.model.entity.ChatMessage;
import com.example.rebookchatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {



}

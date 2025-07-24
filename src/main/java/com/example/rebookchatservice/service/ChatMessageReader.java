package com.example.rebookchatservice.service;

import com.example.rebookchatservice.exception.CMissingDataException;
import com.example.rebookchatservice.repository.ChatMessageRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChatMessageReader {
    private final ChatMessageRepository chatMessageRepository;
}

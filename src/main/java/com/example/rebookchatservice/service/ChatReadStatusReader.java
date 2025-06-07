package com.example.rebookchatservice.service;

import com.example.rebookchatservice.exception.CMissingDataException;
import com.example.rebookchatservice.model.entity.ChatReadStatus;
import com.example.rebookchatservice.model.entity.compositekey.ChatReadStatusId;
import com.example.rebookchatservice.repository.ChatReadStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatReadStatusReader {

    private final ChatReadStatusRepository chatReadStatusRepository;

    public ChatReadStatus findById(ChatReadStatusId statusId) {
        return chatReadStatusRepository.findById(statusId).orElseThrow(CMissingDataException::new);
    }
}

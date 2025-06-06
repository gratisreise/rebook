package com.example.rebookchatservice.controller;

import com.example.rebookchatservice.common.CommonResult;
import com.example.rebookchatservice.common.ResponseService;
import com.example.rebookchatservice.service.ChatReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatReadStatusController {
    // @RquestParam
    // @RequestHeader("X-User-Id) String myId

    private final ChatReadStatusService chatReadStatusService;

    //채팅방 읽기 업데이트
    @PatchMapping("/reads/{roomId}")
    public CommonResult patchLastRead(@PathVariable Long roomId){
        chatReadStatusService.patchLastRead(roomId);
        return ResponseService.getSuccessResult();
    }
}

package com.example.rebookchatservice.controller;

import com.example.rebookchatservice.common.CommonResult;
import com.example.rebookchatservice.common.ResponseService;
import com.example.rebookchatservice.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatRoomController {

    // @RquestParam
    // @RequestHeader("X-User-Id) String myId

    private final ChatRoomService chatRoomService;

    //채팅방 생성
    @PostMapping("/{yourId}")
    public CommonResult createChatRoom(@RequestParam String myId, @PathVariable String yourId) {
        chatRoomService.createChatRoom(myId, yourId);
        return ResponseService.getSuccessResult();
    }

    //채팅방 목록조회


}

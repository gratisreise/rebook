package com.example.rebookchatservice.controller;

import com.example.rebookchatservice.common.CommonResult;
import com.example.rebookchatservice.common.ListResult;
import com.example.rebookchatservice.common.PageResponse;
import com.example.rebookchatservice.common.ResponseService;
import com.example.rebookchatservice.common.SingleResult;
import com.example.rebookchatservice.model.ChatRoomResponse;
import com.example.rebookchatservice.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Sin;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping
    public SingleResult<PageResponse<ChatRoomResponse>> getMyChatRooms(
        @RequestParam String myId,
        @PageableDefault Pageable pageable
    ){
        return ResponseService.getSingleResult(chatRoomService.getMyChatRooms(myId, pageable));
    }


}

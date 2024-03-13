package com.server.controller;

import com.server.service.ChattingRoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChattingRoomController {

    private final ChattingRoomService chattingRoomService;
}

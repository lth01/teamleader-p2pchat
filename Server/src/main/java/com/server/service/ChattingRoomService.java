package com.server.service;

import com.server.repository.ChattingRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChattingRoomService {

    private final ChattingRoomRepository chattingRoomRepository;
}

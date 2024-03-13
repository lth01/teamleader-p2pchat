package com.server.repository;

import com.server.repository.mappers.ChattingRoomMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ChattingRoomRepository {

    private final ChattingRoomMapper chattingRoomMapper;
}

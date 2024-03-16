package com.server.repository.mappers;

import com.server.admin.ChattingRoom;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChattingRoomMapper {

    List<ChattingRoom> getAllChattingRoom();

    Optional<ChattingRoom> getChattingRoom(ChattingRoom chattingRoom);

    Integer createChattingRoom(ChattingRoom chattingRoom);

    Integer updateChattingRoomInfo(ChattingRoom chattingRoom);

    Integer deleteChattingRoom(String roomName);

    Boolean isExistChattingRoom(String roomName);
}

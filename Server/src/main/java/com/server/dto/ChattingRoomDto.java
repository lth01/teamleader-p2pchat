package com.server.dto;

import com.server.admin.ChattingRoom;
import com.server.admin.PeerInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoomDto extends Response{

    private Long id;
    private String roomName;
    private List<PeerInfo> peers;

    @Builder
    public ChattingRoomDto(Boolean status, String message, ChattingRoom chattingRoom, List<PeerInfo> peers) {
        super(status, message);
        this.id = chattingRoom.getId();
        this.roomName = chattingRoom.getRoomName();
        this.peers = peers;
    }
}

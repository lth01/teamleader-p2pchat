package com.server.admin;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoom {

    private Long id;
    private String roomName;
    private Integer maximum;
    private Integer currentPeers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ChattingRoom(String roomName, Integer maximum) {
        this.roomName = roomName;
        this.maximum = maximum;
    }
}

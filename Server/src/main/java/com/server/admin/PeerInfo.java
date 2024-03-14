package com.server.admin;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PeerInfo {

    private Long id;
    private String nickname;
    private String ip;
    private Integer port;
    private Long currentChattingRoomId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PeerInfo(String nickname, String ip, Integer port, Long currentChattingRoomId) {
        this.nickname = nickname;
        this.ip = ip;
        this.port = port;
        this.currentChattingRoomId = currentChattingRoomId;
    }
}

package com.server.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PeerInfo {

    private Long id;
    private String nickname;
    private String ip;
    private int port;
    private Long currentChattingRoomId;
}

package com.servercommunication;

import com.peer.info.PeerInfo;

import java.util.List;

public interface ServerCommunicatorInter {

    String createChattingRoom(String roomName, int maximum);

    List<PeerInfo> connectChattingRoom(String roomName);

    String disconnectChattingRoom(String roomName);

    List<String> getCurrentChattingRoom();
}

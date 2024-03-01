package com.servercommunication;

import java.util.List;

public interface ServerCommunicatorInter {

    void createChattingRoom(String roomName, int maximum);

    List<Peer> connectChattingRoom(String roomName);

    void disconnectChattingRoom();

    void getCurrentChattingRoom();
}

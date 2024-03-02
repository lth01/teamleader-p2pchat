package com.servercommunication;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public interface ServerCommunicatorInter {

    JsonNode createChattingRoom(String roomName, int maximum);

    List<Peer> connectChattingRoom(String roomName);

    JsonNode disconnectChattingRoom();

    List<String> getCurrentChattingRoom();
}

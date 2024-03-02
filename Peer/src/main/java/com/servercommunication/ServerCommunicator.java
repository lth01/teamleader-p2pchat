package com.servercommunication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.util.JsonParser;
import com.util.Validation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerCommunicator implements ServerCommunicatorInter {

    private Peer peerInfo;
    private String serverIp;
    private int serverPort;
    // private boolean isConnectToChattingRoom = false;
    // private String response = null;
    private URL serverURL;
    private String baseURL = "";

    public ServerCommunicator(Peer peerInfo, String serverIp, int serverPort)
        throws IllegalArgumentException{
        this.peerInfo = peerInfo;
        this.serverPort = serverPort;

        if (!Validation.validateIp(serverIp)) {
            throw new IllegalArgumentException();
        }

        this.serverIp = serverIp;
        this.baseURL = "https://" + this.serverIp + ":" + this.serverPort;
    }

    @Override
    public JsonNode createChattingRoom(String roomName, int maximum) {
        String[] keys = {"nickname", "room_name", "maximum"};
        String[] values = {peerInfo.getNickname(), roomName, Integer.toString(maximum)};
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        return HttpConnection.sendPOSTRequest(serverURL, setData(keys, values));

//        ToDo: 반환값을 각 상황에 맞는 Java 객체로 던져주기
//        if(response.has("Error")) {
//            System.out.println(response.get("Error").asText());
//            return;
//        }
//
//        System.out.println(response.get("Status").asText());
    }

    @Override
    public List<Peer> connectChattingRoom(String roomName) {
        String[] keys = {"room_name"};
        String[] values = {roomName};
        List<Peer> retVal = new ArrayList<>();
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        JsonNode response = HttpConnection.sendPOSTRequest(serverURL, setData(keys, values));

        if (!response.has("Peers")) {
            return null;
        }

        // ToDo: Peers의 value인 Peer 리스트가 리스트 형식인지 String 형식인지 테스트 필요
        response.get("Peers").forEach(peer -> {
            try {
                retVal.add(JsonParser.convertJsonNodeToPeer(peer));
            } catch (JsonProcessingException ignored) {}
        });

        return retVal;
    }

    // ToDo: 반환값을 각 상황에 맞는 Java 객체로 던져주기
    @Override
    public JsonNode disconnectChattingRoom() {
        String[] keys = {"nickname"};
        String[] values = {peerInfo.getNickname()};
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        return HttpConnection.sendPOSTRequest(serverURL, setData(keys, values));
    }

    @Override
    public List<String> getCurrentChattingRoom() {
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        JsonNode response = HttpConnection.sendGETRequest(serverURL);
        List<String> retVal = new ArrayList<>();

        if (!response.has("Rooms")) {
            return null;
        }

        // ToDo: Rooms의 value인 String 리스트가 리스트 형식인지 String 형식인지 테스트 필요
        response.get("Rooms").forEach(room -> {
            retVal.add(room.toString());
        });

        return retVal;
    }

    private void createServerURL(String endPoint){
        try {
            serverURL = new URL(baseURL + "/" + endPoint);
        } catch (MalformedURLException e) {
            serverURL = null;
        }
    }

    private Map<String, String> setData(String[] keys, String[] values) {
        Map<String, String> retVal = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            retVal.put(keys[i], values[i]);
        }

        return retVal;
    }
}

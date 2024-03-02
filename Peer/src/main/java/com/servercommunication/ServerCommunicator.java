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

    /**
     * <p>서버와의 통신을 위한 ServerCommunicator의 생성자입니다.</p>
     * <p>올바른 형식의 서버 IP를 입력하지 않으면 예외를 발생시킵니다.</p>
     *
     * @param peerInfo Peer의 정보를 담은 Peer 객체
     * @param serverIp 연결하고자 하는 서버의 IP
     * @param serverPort 연결하고자 하는 서버의 Port 번호
     * @throws IllegalArgumentException
     */
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

    /**
     * <p>만들고자 하는 채팅방의 이름과 최대 인원수를 입력받으면 서버와 통신해 채팅방을 생성합니다.</p>
     *
     * @param roomName 만들고자 하는 채팅방 이름
     * @param maximum 설정하고자 하는 채팅방의 최대 인원수
     * @return 서버의 Response (아직 반환값을 확정하지 못함)
     */
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

    /**
     * <p>연결하고자 하는 채팅방 이름을 입력받으면 서버와 통신해 해당 채팅방에 접속한 Peer들의 정보 List를 반환합니다.</p>
     * <p>만약 통신 도중 문제가 생겼다면 null을 반환합니다.</p>
     *
     * @param roomName 연결하고자 하는 채팅방 이름
     * @return 현재 채팅방에 연결된 Peer들의 List / 서버의 response를 제대로 못받을 경우 null
     */
    @Override
    public List<Peer> connectChattingRoom(String roomName) {
        String[] keys = {"room_name", "nickname"};
        String[] values = {roomName, peerInfo.getNickname()};
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

    /**
     * <p>서버와 통신해 현재 접속해있는 채팅방에서 Peer의 정보를 삭제합니다.</p>
     *
     * @return 서버의 Response
     */
    // ToDo: 반환값을 각 상황에 맞는 Java 객체로 던져주기
    @Override
    public JsonNode disconnectChattingRoom() {
        String[] keys = {"nickname"};
        String[] values = {peerInfo.getNickname()};
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        return HttpConnection.sendPOSTRequest(serverURL, setData(keys, values));
    }

    /**
     * <p>서버와 통신해 현재 활성화된 채팅방 목록을 반환합니다.</p>
     * <p>만약 도중에 통신 오류가 날 경우 null을 반환합니다.</p>
     *
     * @return 현재 활성화된 채팅방 이름 List / 서버 통신 오류일 경우 null
     */
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

    // 접속하고자 하는 서버의 end point로의 URL을 설정하는 메소드
    private void createServerURL(String endPoint){
        try {
            serverURL = new URL(baseURL + "/" + endPoint);
        } catch (MalformedURLException e) {
            serverURL = null;
        }
    }

    // key-value 쌍으로 map을 만드는 메소드
    private Map<String, String> setData(String[] keys, String[] values) {
        Map<String, String> retVal = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            retVal.put(keys[i], values[i]);
        }

        return retVal;
    }
}

package com.servercommunication;

import com.peer.info.PeerInfo;
import com.peer.message.*;
import com.util.JsonParser;
import com.util.Validation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ServerCommunicator implements ServerCommunicatorInter {

    private PeerInfo peerInfo;  // 사용자 정보 객체
    private String serverIp;    // 연결할 서버 IP
    private int serverPort;     // 연결할 서버 Port 번호
    // private boolean isConnectToChattingRoom = false;
    // private String response = null;
    private URL serverURL;      // 연결할 서버의 URL
    private String baseURL = "";    // end-point가 없는 기본 URL

    /**
     * <p>서버와의 통신을 위한 ServerCommunicator의 생성자입니다.</p>
     * <p>올바른 형식의 서버 IP를 입력하지 않으면 예외를 발생시킵니다.</p>
     *
     * @param peerInfo Peer의 정보를 담은 Peer 객체
     * @param serverIp 연결하고자 하는 서버의 IP
     * @param serverPort 연결하고자 하는 서버의 Port 번호
     * @throws IllegalArgumentException
     */
    public ServerCommunicator(PeerInfo peerInfo, String serverIp, int serverPort)
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
     * @return 서버의 Response
     */
    @Override
    public String createChattingRoom(String roomName, int maximum) {
        // 서버로 보낼 CreatMessage 객체 생성
        CreateMessage data = new CreateMessage(peerInfo.getNickname(), roomName, maximum);
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        // POST로 Request 보냄
        String json = HttpConnection.sendPOSTRequest(serverURL, data);
        // 받은 데이터를 ResultResponse 객체로 Parsing
        ResultResponse response = (ResultResponse) JsonParser.convertJsonStringToMessage(json, "ResultResponse");

        if (response == null) {
            return "Json Parsing Error!";
        }

        // Status key의 value를 반환
        return response.getStatus();
    }

    /**
     * <p>연결하고자 하는 채팅방 이름을 입력받으면 서버와 통신해 해당 채팅방에 접속한 Peer들의 정보 List를 반환합니다.</p>
     * <p>만약 통신 도중 문제가 생겼다면 null을 반환합니다.</p>
     *
     * @param roomName 연결하고자 하는 채팅방 이름
     * @return 현재 채팅방에 연결된 Peer들의 List / 서버의 response를 제대로 못받을 경우 null
     */
    @Override
    public List<PeerInfo> connectChattingRoom(String roomName) {
        // 서버로 보낼 ConnectMessage 객체 생성
        ConnectMessage data = new ConnectMessage(peerInfo.getNickname(), roomName);
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        // POST로 Request를 보냄
        String json = HttpConnection.sendPOSTRequest(serverURL, data);
        // 받은 데이터를 Message 객체로 Parsing
        Message response = JsonParser.convertJsonStringToMessage(json, "ConnectResponse");

        // 이 Message가 ResultResponse라면 통신이 제대로 이루어지지 않음
        if (response instanceof ResultResponse) {
            return null;
        }

        // ResultResponse 객체가 아니라면 ConnectResponse 객체로 변환 후 PeerInfo 리스트를 반환
        return ((ConnectResponse) response).getPeer();
    }

    /**
     * <p>서버와 통신해 현재 접속해있는 채팅방에서 Peer의 정보를 삭제합니다.</p>
     *
     * @return 서버의 Response
     */
    @Override
    public String disconnectChattingRoom(String roomName) {
        // 서버로 보낼 ConnectMessage 객체 생성
        ConnectMessage data = new ConnectMessage(peerInfo.getNickname(), roomName);
        // ToDo: 서버의 각 상황에 맞는 API로 end point 작성
        createServerURL("");

        // POST로 Request를 보냄
        String json = HttpConnection.sendPOSTRequest(serverURL, data);
        // 받은 데이터를 Message 객체로 Parsing
        Message response = JsonParser.convertJsonStringToMessage(json, "DisconnectMessage");

        // 이 Message가 ResultResponse라면 통신이 제대로 이루어지지 않음
        // ResultResponse 객체로 변환 후 상태 메시지를 반환
        if (response instanceof ResultResponse) {
            return ((ResultResponse) response).getStatus();
        }

        if (response == null) {
            return "Json Parsing Error!";
        }

        // ResultResponse 객체가 아니라면 DisconnectMessage 객체로 변환 후 메시지를 반환
        return ((DisconnectMessage) response).getMessage();
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

        // GET으로 Request를 보냄
        String json = HttpConnection.sendGETRequest(serverURL);
        // 받은 데이터를 Message 객체로 Parsing
        Message response = JsonParser.convertJsonStringToMessage(json, "CurrentChattingRoomResponse");

        // 이 Message가 ResultResponse라면 통신이 제대로 이루어지지 않음
        if (response instanceof ResultResponse) {
            return null;
        }

        // ResultResponse 객체가 아니라면 CurrentChattingRoomResponse 객체로 변환 후 현재 활성화된 채팅방 이름 리스트 반환
        return ((CurrentChattingRoomResponse) response).getRooms();
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
//    private Map<String, String> setData(String[] keys, String[] values) {
//        Map<String, String> retVal = new HashMap<>();
//
//        for (int i = 0; i < keys.length; i++) {
//            retVal.put(keys[i], values[i]);
//        }
//
//        return retVal;
//    }
}

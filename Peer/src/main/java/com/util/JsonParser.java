package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.servercommunication.Peer;
import java.util.Map;
import java.util.Map.Entry;
import com.message.*;

/**
 * Json Parsing에 대한 메소드를 모아놓은 Util 클래스입니다.
 */
public class JsonParser {

    // Jackson 라이브러리의 ObjectMapper 클래스를 사용
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 생성자 사용 방지
    private JsonParser() {
    }

    /**
     * <p>Java Object를 String 타입의 json 포멧으로 변환시키는 메소드</p>
     * <p>이때, json 포멧으로 변경 실패할 시 예외를 발생시킵니다.</p>
     *
     * @param object json string 형식으로 변환하고자 하는 Java Object
     * @return json string 형식으로 변환된 Java Object
     * @throws JsonProcessingException
     */
    public static <T> String convertObjectToJsonString(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * <p>Json String을 JsonNode 객체로 변환시키는 메소드</p>
     * <p>이때, JsonNode 객체로 변경 실패 시 예외를 발생시킵니다.</p>
     *
     * @param json JsonNode로 변환시키고자 하는 Json String
     * @return 변환된 JsonNode 객체
     * @throws JsonProcessingException
     */
    public static JsonNode convertJsonStringToJsonNode(String json) throws JsonProcessingException {
        return objectMapper.readTree(json);
    }

    /**
     * <p>JsonNode 객체를 Peer 객체로 변환시키는 메소드</p>
     * <p>이때, Peer 객체로 변경 실패 시 예외를 발생시킵니다.</p>
     *
     * @param json Peer 객체로 변환시키고자 하는 JsonNode 객체
     * @return 변환된 Peer 객체
     * @throws JsonProcessingException
     */
    public static Peer convertJsonNodeToPeer(JsonNode json) throws JsonProcessingException {
        return objectMapper.readValue(json.toString(), Peer.class);
    }

    /**
     * <p>Map 데이터를 JsonNode 객체로 변환시키는 메소드</p>
     *
     * @param data JsonNode로 변환시키고자 하는 데이터
     * @return 변환된 JsonNode 객체
     */
    public static JsonNode getJsonNode(Map<String, String> data) {
        ObjectNode retVal = objectMapper.createObjectNode();

        for (Entry<String, String> entry : data.entrySet()) {
            retVal.put(entry.getKey(), entry.getValue());
        }

        return retVal;
    }

    /**
     * <p>messageType에 맞는 객체로 Json String을 변환시킵니다.</p>
     * <p>지정한 messageType과 다른 messageType이 들어온 경우 ResultResponse 객체로 변환시킵니다.</p>
     * <p>Parsing 도중 문제가 생기면 null을 반환합니다.</p>
     * <p>---------------------------------------------</p>
     * <p>지정된 messageType: {ConnectMessage, ConnectResponse, CreateMessage, CurrentChattingRoomResponse, DisconnectMessage, TalkMessage, ResultResponse}</p>
     *  <p>---------------------------------------------</p>
     *
     * @param json 변환하고자 하는 Json String
     * @param messageType 변환하고자 하는 메시지 객체 타입 이름
     * @return 변환된 Message 객체 / Parsing 도중 문제가 생길 경우 null
     */
    public static Message convertJsonStringToMessage(String json, String messageType) {
        Message retVal = null;

        try {
            retVal = switch (messageType) {
                case "ConnectMessage" -> convertJsonStringToConnectMessage(json);
                case "ConnectResponse" -> convertJsonStringToConnectResponse(json);
                case "CreateMessage" -> convertJsonStringToCreateMessage(json);
                case "CurrentChattingRoomResponse" ->
                    convertJsonStringToCurrentChattingRoomResponse(json);
                case "DisconnectMessage" -> convertJsonStringToDisconnectMessage(json);
                case "TalkMessage" -> convertJsonStringToTalkMessage(json);
                default -> convertJsonStringToResultResponse(json);
            };
        } catch (JsonProcessingException e) {
            retVal = null;
        }

        return retVal;
    }

    private static ConnectMessage convertJsonStringToConnectMessage(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, ConnectMessage.class);
    }

    private static ConnectResponse convertJsonStringToConnectResponse(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, ConnectResponse.class);
    }

    private static CreateMessage convertJsonStringToCreateMessage(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, CreateMessage.class);
    }

    private static CurrentChattingRoomResponse convertJsonStringToCurrentChattingRoomResponse(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, CurrentChattingRoomResponse.class);
    }

    private static DisconnectMessage convertJsonStringToDisconnectMessage(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, DisconnectMessage.class);
    }

    private static TalkMessage convertJsonStringToTalkMessage(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, TalkMessage.class);
    }

    private static ResultResponse convertJsonStringToResultResponse(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, ResultResponse.class);
    }
}

package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.servercommunication.Peer;
import java.util.Map;
import java.util.Map.Entry;

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
}

package com.utilTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.message.ConnectMessage;
import com.message.ConnectResponse;
import com.message.CreateMessage;
import com.message.CurrentChattingRoomResponse;
import com.message.DisconnectMessage;
import com.message.Message;
import com.message.ResultResponse;
import com.message.TalkMessage;
import com.servercommunication.Peer;
import com.util.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JsonParserTest {

    @DisplayName("convertObjectToJsonString() 메소드 테스트")
    @Test
    void convertObjectToJsonStringTest() {
        List<Peer> peers = List.of(new Peer("test1", "1.1.1.1", 8080),
            new Peer("test2", "127.0.0.1", 5069),
            new Peer("test3", "83.23.103.64", 10050),
            new Peer("test4", "79.24.123.52", 10042),
            new Peer("test5", "192.108.111.10", 5390));
        List<String> correct = List.of("{\"nickname\":\"test1\",\"ip\":\"1.1.1.1\",\"port\":8080}",
            "{\"nickname\":\"test2\",\"ip\":\"127.0.0.1\",\"port\":5069}",
            "{\"nickname\":\"test3\",\"ip\":\"83.23.103.64\",\"port\":10050}",
            "{\"nickname\":\"test4\",\"ip\":\"79.24.123.52\",\"port\":10042}",
            "{\"nickname\":\"test5\",\"ip\":\"192.108.111.10\",\"port\":5390}");
        List<String> result = new ArrayList<>();

        peers.forEach(peer -> Assertions.assertDoesNotThrow(() -> {
            result.add(JsonParser.convertObjectToJsonString(peer));
        }));

        Assertions.assertArrayEquals(result.toArray(), correct.toArray());
    }

    @DisplayName("convertJsonStringToJsonNode() 메소드 테스트")
    @Test
    void convertJsonStringToJsonNodeTest() {
        List<String> peers = List.of("{\"nickname\":\"test1\",\"ip\":\"1.1.1.1\",\"port\":8080}",
            "{\"nickname\":\"test2\",\"ip\":\"127.0.0.1\",\"port\":5069}",
            "{\"nickname\":\"test3\",\"ip\":\"83.23.103.64\",\"port\":10050}",
            "{\"nickname\":\"test4\",\"ip\":\"79.24.123.52\",\"port\":10042}",
            "{\"nickname\":\"test5\",\"ip\":\"192.108.111.10\",\"port\":5390}");

        peers.forEach(peer -> Assertions.assertDoesNotThrow(() -> {
            JsonParser.convertJsonStringToJsonNode(peer);
        }));
    }

    @DisplayName("convertJsonNodeToPeer() 메소드 테스트")
    @Test
    void convertJsonNodeToPeerTest() {
        List<Map<String, String>> testMaps = new ArrayList<>();
        List<String> testIp = List.of("1.1.1.1", "127.0.0.1", "83.23.103.64", "79.24.123.52",
            "192.108.111.10");
        List<String> testPort = List.of("8080", "5069", "10050", "10042", "5390");

        for (int i = 0; i < testIp.size(); i++) {
            Map<String, String> peer = new HashMap<>();
            peer.put("nickname", "test" + (i + 1));
            peer.put("ip", testIp.get(i));
            peer.put("port", testPort.get(i));
            testMaps.add(peer);
        }

        for (int i = 0; i < testMaps.size(); i++) {
            Map<String, String> peer = testMaps.get(i);
            JsonNode jsonNode = JsonParser.getJsonNode(peer);
            String ip = testIp.get(i);
            int port = Integer.parseInt(testPort.get(i));

            Assertions.assertDoesNotThrow(() -> {
                Peer convertedPeer = JsonParser.convertJsonNodeToPeer(jsonNode);

                Assertions.assertEquals(convertedPeer.getIp(), ip);
                Assertions.assertEquals(convertedPeer.getPort(), port);
            });
        }
    }

    @DisplayName("getJsonNode() 메소드 테스트")
    @Test
    void getJsonNodeTest() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("Name", "Test");
        testMap.put("ID", "10");
        testMap.put("Test_List", List.of(1, 2, 3, 4, 5).toString());

        JsonNode testNode = JsonParser.getJsonNode(testMap);

        Assertions.assertTrue(testNode.has("Name"));
        Assertions.assertTrue(testNode.has("ID"));
        Assertions.assertTrue(testNode.has("Test_List"));

        Assertions.assertEquals("Test", testNode.get("Name").asText());
        Assertions.assertEquals("10", testNode.get("ID").asText());
        Assertions.assertEquals("[1, 2, 3, 4, 5]", testNode.get("Test_List").asText());

    }

    @DisplayName("convertJsonStringToMessage() 메소드 테스트")
    @Test
    void convertJsonStringToMessageTest() {
        String test1 = "{\"name\":\"test1\","
            + "\"type\":\"CONNECT\","
            + "\"roomName\":\"test room 1\"}";
        String test2 = "{\"name\":\"\","
            + "\"type\":\"CONNECTRESPONSE\","
            + "\"peer\":[{\"nickname\":\"test2\",\"ip\":\"1.2.3.4\",\"port\":10030},"
            + "{\"nickname\":\"test3\",\"ip\":\"190.108.23.56\",\"port\":8080},"
            + "{\"nickname\":\"test4\",\"ip\":\"255.255.255.255\",\"port\":3818}]}";
        String test5 = "{\"name\":\"test5\","
            + "\"type\":\"CREATEREQUEST\","
            + "\"roomName\":\"test room 2\","
            + "\"maximum\":8}";
        String test6 = "{\"name\":\"test6\","
            + "\"type\":\"CURRENTCHATTINGROOMRESPONSE\","
            + "\"rooms\":[\"test room 1\",\"test room 2\",\"test room 3\",\"test room 4\",\"test room 5\"]}";
        String test7 = "{\"name\":\"test7\","
            + "\"type\":\"DISCONNECT\","
            + "\"message\":\"Disconnect complete\"}";
        String test8 = "{\"name\":\"test8\","
            + "\"name\":\"test8\","
            + "\"type\":\"TALK\","
            + "\"sendTime\":12343.121,"
            + "\"message\":\"Hello?\"}";
        String test9 = "{\"name\":\"test9\","
            + "\"type\":\"RESULTRESPONSE\","
            + "\"status\":\"Done\"}";
        String test10 = "{\"name\":\"test10\","
            + "\"type\":\"RESULTRESPONSE\","
            + "\"status\":\"Error : 404\"}";

        Map<String, String> testMap = new HashMap<>();
        testMap.put("ConnectMessage", test1);
        testMap.put("ConnectResponse", test2);
        testMap.put("CreateMessage", test5);
        testMap.put("CurrentChattingRoomResponse", test6);
        testMap.put("DisconnectMessage", test7);
        testMap.put("TalkMessage", test8);
        testMap.put("ResultResponse", test9);
        testMap.put("NotMessage", test10);

        testMap.forEach((key, value) -> {
            Assertions.assertDoesNotThrow(() -> {
                Message json = JsonParser.convertJsonStringToMessage(value, key);
                if (!(json instanceof ConnectMessage
                    || json instanceof ConnectResponse
                    || json instanceof CreateMessage
                    || json instanceof CurrentChattingRoomResponse
                    || json instanceof DisconnectMessage
                    || json instanceof TalkMessage
                    || json instanceof ResultResponse)) {
                    throw new Exception();
                }
            });
        });
    }
}

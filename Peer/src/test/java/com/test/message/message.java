package com.test.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peer.message.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class message {
    @Test
    public void serialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String talkJson = "{\"sendTime\":1,\"type\":\"TALK\",\"message\":\"텍스트12\", \"name\":\"김병용\"}";
        String disconnectJson = "{\"type\":\"DISCONNECT\",\"message\":\"연결종료 메세지\", \"name\":\"김병용2\"}";
        String connectResponseJson = "{\"type\":\"CONNECTRESPONSE\",\"peer\":[{\"nickname\":\"lth\",\"ip\":\"127.0.0.1\",\"port\":8080}]}";
        String createRoomJson = "{\"type\":\"CREATEREQUEST\",\"roomName\":\"방1\"}";
        String resultJson = "{\"type\":\"RESULTRESPONSE\",\"status\":\"200 OK\"}";
        String roomResponseJson = "{\"type\":\"CURRENTCHATTINGROOMRESPONSE\",\"rooms\":[\"hi\", \"hello\"]}";
        Message msg = mapper.readValue(talkJson, Message.class);
        Message msg2 = mapper.readValue(disconnectJson, Message.class);
        Message msg3 = mapper.readValue(connectResponseJson, Message.class);
        Message msg4 = mapper.readValue(createRoomJson, Message.class);
        Message msg5 = mapper.readValue(resultJson, Message.class);
        Message msg6 = mapper.readValue(roomResponseJson, Message.class);
        String str1 = mapper.writeValueAsString(msg);
        String str2 = mapper.writeValueAsString(msg2);
        String str3 = mapper.writeValueAsString(msg3);
        String str4 = mapper.writeValueAsString(msg4);
        String str5 = mapper.writeValueAsString(msg5);
        String str6 = mapper.writeValueAsString(msg6);


        System.out.println(msg);
        System.out.println(msg2);
        System.out.println(msg3);
        System.out.println(msg4);
        System.out.println(msg5);
        System.out.println(msg6);

        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
        System.out.println(str4);
        System.out.println(str5);
        System.out.println(str6);
    }

}

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
        Message msg = mapper.readValue(talkJson, Message.class);
        Message msg2 = mapper.readValue(disconnectJson, Message.class);


        System.out.println(msg);
        System.out.println(msg2);
    }

}

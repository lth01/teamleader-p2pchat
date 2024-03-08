package com.peer.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TalkMessage.class, name = "TALK"),
        @JsonSubTypes.Type(value = DisconnectMessage.class, name = "DISCONNECT"),
        @JsonSubTypes.Type(value = ConnectMessage.class, name = "CONNECT"),
        @JsonSubTypes.Type(value = ConnectResponse.class, name = "CONNECTRESPONSE"),
        @JsonSubTypes.Type(value = CreateMessage.class, name = "CREATEREQUEST"),
        @JsonSubTypes.Type(value = ResultResponse.class, name = "RESULTRESPONSE"),
        @JsonSubTypes.Type(value = CurrentChattingRoomResponse.class, name = "CURRENTCHATTINGROOMRESPONSE")
})
public abstract class Message {
    private String name;

    private MessageType type;


    protected Message(){}

    public Message(String name, MessageType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
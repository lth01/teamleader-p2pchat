package com.peer.message;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "DISCONNECT")
public class DisconnectMessage extends Message{
    private String message;

    private DisconnectMessage(){}
    public DisconnectMessage(String name, String message){
        super(name, MessageType.DISCONNECT);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

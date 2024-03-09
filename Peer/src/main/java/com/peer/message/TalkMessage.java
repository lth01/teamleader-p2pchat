package com.peer.message;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "TALK")
public class TalkMessage extends Message{
    private long sendTime;

    private String message;

    private TalkMessage(){}

    public TalkMessage(String name, long sendTime, String message){
        super(name, MessageType.TALK);
        this.sendTime = sendTime;
        this.message = message;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

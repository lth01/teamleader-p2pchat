package com.peer.message;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "CONNECT")
public class ConnectMessage extends Message{
	private String roomName;

	private ConnectMessage(){}

	public ConnectMessage(String name, String roomName){
			super(name, MessageType.CONNECT);
			this.roomName = roomName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}

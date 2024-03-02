package com.peer.message;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "CREATEREQUEST")
public class CreateMessage extends Message{
	static final private int ENTERMAXIMUM = 8;
	private String roomName;
	private int maximum;

	private CreateMessage(){}

	public CreateMessage(String name, String roomName, int maximum){
		super("", MessageType.CREATEREQUEST);
		this.roomName = roomName;
		if(maximum <= 0){
			this.maximum = ENTERMAXIMUM;
		}
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}

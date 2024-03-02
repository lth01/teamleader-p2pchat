package com.peer.message;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "CURRENTCHATTINGROOMREQUEST")
public class CurrentChattingRoomResponse extends Message{
	private List<String> rooms = new ArrayList<>();

	private CurrentChattingRoomResponse(){}

	public CurrentChattingRoomResponse(List<String> rooms){
		super("", MessageType.CURRENTCHATTINGROOMRESPONSE);
		this.rooms = rooms;
	}

	public List<String> getRooms() {
		return rooms;
	}

	public void setRooms(List<String> rooms) {
		this.rooms = rooms;
	}
}

package com.peer.message;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.peer.info.PeerInfo;

@JsonTypeName(value = "CONNECTRESPONSE")
public class ConnectResponse extends Message{
	private List<PeerInfo> peer = new ArrayList<>();

	private ConnectResponse(){}

	@ConstructorProperties({"peer"})
	public ConnectResponse(List<PeerInfo> peer){
		super("", MessageType.CONNECTRESPONSE);
		this.peer = peer;
	}

	public List<PeerInfo> getPeer() {
		return peer;
	}

	public void setPeer(List<PeerInfo> peer) {
		this.peer = peer;
	}
}

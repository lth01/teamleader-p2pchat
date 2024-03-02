package com.peer.message;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "RESULTRESPONSE")
public class ResultResponse extends Message{
	private String status;

	private ResultResponse(){}

	public ResultResponse(String status) {
		super("", MessageType.RESULTRESPONSE);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

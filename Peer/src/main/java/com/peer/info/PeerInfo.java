package com.peer.info;

public class PeerInfo {
	private String nickname;

	private String ip;

	private int port;

	private PeerInfo(){}

	public PeerInfo(String nickname, String ip, int port) {
		this.nickname = nickname;
		this.ip = ip;
		this.port = port;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

package com.peer.peerservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.peer.connection.Connection;
import com.peer.info.PeerInfo;
import com.peer.message.*;

public class PeerServiceImpl implements PeerService{
	private List<Connection> connections;


	public PeerServiceImpl(ServerSocket sc){
		connections = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	public boolean doConnect(Socket sc, PeerInfo peerInfo) {
		Connection newConnection = Connection.doConnect(sc, this, peerInfo);
		connections.add(newConnection);
		newConnection.writeMessage(new ConnectMessage("",""));
		newConnection.run();
		return false;
	}

	@Override
	public boolean doDisConnect() {
		for(Connection connection : connections){
			connection.writeMessage(new DisconnectMessage("",""));
			connection.doDisConnect();
		}
		return false;
	}

	@Override
	public boolean sendMessgeToPeer(Message message) {
		connections.forEach(connection -> connection.writeMessage(message));
		return false;
	}

	//Connection에서 호출.
	@Override
	public boolean recvMessageFromPeer(Message message) {
		switch (message.getType()){
			case DISCONNECT -> extractOtherPeer(message.getName());
			case TALK -> handleTalk((TalkMessage)message);
		}

		return false;
	}

	//상대 peer의 닉네임으로 축출한다.
	public boolean extractOtherPeer(String nickName){
		int exactPeerIdx = -1;
		for(int idx = 0; idx < connections.size(); idx++){
			PeerInfo otherPeer = connections.get(idx).getOtherPeer();
			if(otherPeer.getNickname().equals(nickName)){
				exactPeerIdx = idx;
			}
		}

		if(exactPeerIdx != -1){
			connections.get(exactPeerIdx).setThreadFlag(false);
			connections.remove(exactPeerIdx);
			return true;
		}
		return false;
	}

	public void handleTalk(TalkMessage message){
		System.out.println("상대방 닉네임: " + message.getName());
		System.out.println("메세지: " + message.getMessage());
	}
}

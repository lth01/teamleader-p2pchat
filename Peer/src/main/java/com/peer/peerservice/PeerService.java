package com.peer.peerservice;


import java.net.Socket;

import com.peer.info.PeerInfo;
import com.peer.message.Message;

public interface PeerService {
    boolean doConnect(Socket sc, PeerInfo peerInfo);

    boolean doDisConnect();

    boolean sendMessgeToPeer(Message message);

    boolean recvMessageFromPeer(Message message);
}

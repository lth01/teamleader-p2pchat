package com.peer.peerservice;

public interface PeerService {
    boolean doConnection();

    boolean doDisConnect();

    boolean sendMessgeToPeer();

    boolean recvMessageFromPeer();
}

package com.server.repository.mappers;

import com.server.admin.PeerInfo;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeerInfoMapper {

    List<PeerInfo> getAllPeerInfo();

    Optional<PeerInfo> getPeerInfo(PeerInfo peerInfo);

    List<PeerInfo> getPeerInfosByChattingRoomId(String chattingRoomId);

    Integer savePeerInfo(PeerInfo peerInfo);

    Integer updatePeerInfo(PeerInfo peerInfo);

    Integer deletePeerInfo(PeerInfo peerInfo);

    Boolean isExistNicknameInChattingRoom(PeerInfo peerInfo);
}

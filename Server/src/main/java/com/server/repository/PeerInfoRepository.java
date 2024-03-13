package com.server.repository;

import com.server.repository.mappers.PeerInfoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PeerInfoRepository {

    private final PeerInfoMapper peerInfoMapper;
}

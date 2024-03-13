package com.server.service;

import com.server.repository.PeerInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PeerInfoService {

    private final PeerInfoRepository peerInfoRepository;
}

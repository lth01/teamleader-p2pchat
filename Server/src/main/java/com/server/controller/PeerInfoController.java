package com.server.controller;

import com.server.service.PeerInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PeerInfoController {

    private final PeerInfoService peerInfoService;
}

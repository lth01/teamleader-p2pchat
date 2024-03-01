package com.servercommunication;

import static com.util.Validation.validateIp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Peer {

    private String nickname;
    // ip 주소값은 setter 메소드를 제공하지 않음
    @Setter(AccessLevel.NONE)
    private String ip;
    private int port;

    public Peer(String nickname, String ip, int port) throws IllegalArgumentException {
        this.nickname = nickname;
        this.port = port;

        if (!validateIp(ip)) {
            throw new IllegalArgumentException("잘못된 IPv4 주소입니다.");
        }

        this.ip = ip;
    }
}

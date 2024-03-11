package com.main;

import com.peer.info.PeerInfo;
import com.peer.peerservice.PeerService;
import com.peer.peerservice.PeerServiceImpl;
import com.servercommunication.Peer;
import com.servercommunication.ServerCommunicator;
import com.servercommunication.ServerCommunicatorInter;
import com.util.MenuHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;

public class PeerApplication {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket serverSocket = new ServerSocket(9999);
        String nickName = null;
        Integer port = null;
        System.out.println("enter your nickname: ");
        nickName = br.readLine();
        System.out.println("enter your port: ");
        port = Integer.parseInt(br.readLine());
        //temp
        PeerInfo peerInfo = new PeerInfo(nickName, InetAddress.getLocalHost().getHostAddress(), port);
        //내 연결 정보
        PeerService PeerService = new PeerServiceImpl(serverSocket);
        //http server 확인
        ServerCommunicatorInter serverCommunicatorInter = new ServerCommunicator(peerInfo, "127.0.0.1", 8080);

        while (true) {
            System.out.println("메뉴를 선택하세요(모를 경우 Menu입력)");
            String menuStr = null;
            try {
                menuStr = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            switch (menuStr){
                case "Menu" -> printMenus();
                case "Rooms" -> getRooms();
                case "EnterRoom" -> enterRoom();
                case "WriteMsg" -> writeMsg();
            }
        }
    }

    void printMenus(){

    }

    void getRooms(){

    }

    void enterRoom(){

    }

    void writeMsg(){

    }

}

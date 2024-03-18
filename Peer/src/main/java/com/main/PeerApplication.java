package com.main;

import com.peer.info.PeerInfo;
import com.peer.message.TalkMessage;
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
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

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
                case "EnterRoom" -> enterRoom(PeerService ,serverCommunicatorInter, br);
                case "WriteMsg" -> writeMsg();
            }
        }
    }

    static void printMenus(){
        System.out.println("메뉴");
        System.out.println("1. Rooms");
        System.out.println("현재 남아있는 방을 확인합니다.");
        System.out.println("2. EnterRoom");
        System.out.println("방에 접속할 수 있습니다. 들어가려는 방의 이름을 입력합니다.");
        System.out.println("3.WriteMessage");
        System.out.println("메세지를 작성합니다. 방에 들어가있지 않은 경우 전송되지 않습니다.");
    }

    static void getRooms(ServerCommunicatorInter sc){
        List<String> roomList =  sc.getCurrentChattingRoom();

        System.out.println("현재 접근 가능한 채팅방 목록입니다.");
        for(String room : roomList){
            System.out.println("채팅방 이름: " + room);
        }
    }

    static void enterRoom(PeerService ps, ServerCommunicatorInter sc, BufferedReader br) throws IOException {
        System.out.println("접속하려는 방의 이름을 입력해주세요.");
        String roomName = br.readLine();
        List<PeerInfo> list = sc.connectChattingRoom(roomName);
        for(PeerInfo peerInfo : list){
            ps.doConnect(new Socket(peerInfo.getIp(), peerInfo.getPort()), peerInfo);
        }
    }

    static void writeMsg(PeerService ps, ServerCommunicatorInter sc, BufferedReader br, String nickName) throws IOException{
        System.out.println("메세지를 입력해주세요");
        LocalDateTime now = LocalDateTime.now();
        String msg = br.readLine();
        ps.sendMessgeToPeer(new TalkMessage(nickName,(now.getLong(ChronoField.EPOCH_DAY) * 86400000) + now.getLong(
            ChronoField.MILLI_OF_DAY) , msg));
    }

}

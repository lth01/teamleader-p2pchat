package com.peer.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peer.info.PeerInfo;
import com.peer.message.Message;
import com.peer.peerservice.PeerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection extends Thread{
    private boolean threadFlag;
    private PeerService ps;
    private Socket sc;
    private BufferedReader br;
    private ObjectMapper om;
    private PrintWriter pw;

    private PeerInfo otherPeer;

    private Connection(){}

    private Connection(Socket sc, PeerService ps, PeerInfo peerInfo) {
        this.sc = sc;
        this.ps = ps;
        try {
            this.br = new BufferedReader(new InputStreamReader(this.sc.getInputStream()));
            this.pw = new PrintWriter(this.sc.getOutputStream());
            this.om = new ObjectMapper();
            this.otherPeer = peerInfo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PeerInfo getOtherPeer() {
        return otherPeer;
    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }

    public static Connection doConnect(Socket sc, PeerService ps, PeerInfo peerInfo){
        if(sc.isConnected()){
            return new Connection(sc, ps, peerInfo);
        }else{
            return null;
        }
    }

    /**
     * @return 정상적으로 종료될 경우 true, 아닐 경우 false
     */
    public boolean doDisConnect(){
        try{
            if(this.sc.isConnected()){
                this.br = null;
                this.pw = null;
                this.om = null;
                this.sc.close();
                return true;
            }else{
                return false;
            }
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param msg
     * @return
     * @brief
     */
    public boolean writeMessage(Message msg){
		String jsonMsg = null;
		try {
			jsonMsg = om.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		pw.println(jsonMsg);
        pw.flush();
        return false;
    }


    @Override
    public void run() {
        threadFlag = true;
        while(threadFlag){
            try{
                String response = br.readLine();
                Message msg = om.readValue(response, Message.class);
                ps.recvMessageFromPeer(msg);
            }catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
        doDisConnect();
    }
}

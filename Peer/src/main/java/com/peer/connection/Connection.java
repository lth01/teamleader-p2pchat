package com.peer.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peer.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private Socket sc;
    private BufferedReader br;
    private ObjectMapper om;

    private PrintWriter pw;

    private Connection(){}

    private Connection(Socket sc) {
        this.sc = sc;
        try {
            this.br = new BufferedReader(new InputStreamReader(this.sc.getInputStream()));
            this.pw = new PrintWriter(this.sc.getOutputStream());
            this.om = new ObjectMapper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection doConnect(Socket sc){
        if(sc.isConnected()){
            return new Connection(sc);
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
//        String jsonMsg = om.writeValueAsString(msg);

//        pw.println(jsonMsg);
//        pw.flush();
        return false;
    }

}

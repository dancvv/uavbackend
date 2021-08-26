package com.mountain.Socket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
//暴露出去的应用路径
@ServerEndpoint("/myws")
@Component
public class WsServerEndpoint {
    @OnOpen
    public void Open(Session session){
        System.out.println("连接成功");
    }
    @OnClose
    public void onClose(Session session){
        System.out.println("连接关闭");
    }
    @OnMessage
    public String onMsg(String text){
        return "server 发送"+text;
    }
}

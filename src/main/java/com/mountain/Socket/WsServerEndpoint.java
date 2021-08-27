package com.mountain.Socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
//暴露出去的应用路径
@ServerEndpoint("/myws")
@Component
@Slf4j
public class WsServerEndpoint {
    @OnOpen
    public void Open(Session session){
        log.info("有新的客户端连接了");
        System.out.println("连接成功");
    }
    @OnClose
    public void onClose(Session session){
        log.info("有用户断开了");
        System.out.println("连接关闭");
    }
    @OnMessage
    public String onMsg(String text){
        return "server 发送"+text;
    }
}

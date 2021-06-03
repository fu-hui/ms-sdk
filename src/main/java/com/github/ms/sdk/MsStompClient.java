package com.github.ms.sdk;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.stomp.Frame;
import io.vertx.ext.stomp.StompClient;
import io.vertx.ext.stomp.StompClientConnection;

import java.util.Map;

public class MsStompClient {
    private final String username;

    private final String password;

    private Vertx vertx;

    private StompClient client;

    private StompClientConnection stompClientConnection;

    public MsStompClient(String username, String password) {
        this.username = username;
        this.password = password;
        init();
    }

    public void destroy() {
        client.close();
        vertx.close();
    }

    public void init() {
        this.vertx = Vertx.vertx();
        this.client = StompClient.create(vertx);
    }

    public void setMsgHandler(Handler<Frame> handler) {
        if (client == null) {
            System.out.println("set msg receive handler, stompClientConnection is null");
            return;
        }

        client.receivedFrameHandler(handler);
    }

    public void connect(String host, int port, String subAddress) {
        client.options().setLogin(username + ":" + password);
        client.connect(port, host)
                .onSuccess(stompClientConnection -> {
                    this.stompClientConnection = stompClientConnection;
                    System.out.printf("connect %s:%d success\n", host, port);
                    sub(subAddress);
                    stompClientConnection.closeHandler((clientConnection) -> {
                        System.out.println("connection close");
                        destroy();
                    });
                })
                .onFailure(throwable -> {
                    System.out.printf("connect %s:%d error\n", host, port);
                });
    }

    public void sub(String subAddress) {
        if (stompClientConnection == null) {
            System.out.println("sub error, stompClientConnection is null");
            return;
        }
        stompClientConnection.subscribe(subAddress, frame -> {});
    }

    public void sendMsg(String destination, Map<String, String> headers, String msg) {
        if (stompClientConnection == null) {
            System.out.println("send msg error, stompClientConnection is null");
            return;
        }
        stompClientConnection.send(destination, headers, Buffer.buffer(msg));
    }
}

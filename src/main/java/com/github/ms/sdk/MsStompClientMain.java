package com.github.ms.sdk;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class MsStompClientMain {
    public static void main(String[] args) throws InterruptedException {
        String username = System.getenv("username");
        String to = System.getenv("to");
        String password = "123456";
        String subAddress = "broadcast/rtm";
        String sendAddress = "msg/rtm";
        int port = 18080;
        String host = "152.136.192.15";

        //
        MsStompClient msStompClient = new MsStompClient(username, password);
        msStompClient.setMsgHandler(frame -> {
            switch (frame.getCommand()) {
                case PING:
                    return;
                case MESSAGE:
                    System.out.println("message frame.toString() = " + frame);
                    break;
            }
        });

        msStompClient.connect(host, port, subAddress);

        while (true) {
            System.out.println("please input next command:");
            Scanner sc = new Scanner(System.in);
            String next = sc.next().trim();
            if ("quit".equals(next)) {
                break;
            }

            MsRecord msRecord = new MsRecord();
            msRecord.setFrom(username);
            msRecord.setToList(Arrays.asList(to));
            msRecord.setContent(next);
            Gson gson = new Gson();
            String msg = gson.toJson(msRecord);
            System.out.println(msg);
            msStompClient.sendMsg(sendAddress, new HashMap<>(), msg);
        }
    }
}

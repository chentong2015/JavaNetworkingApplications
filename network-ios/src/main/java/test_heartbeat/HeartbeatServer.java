package test_heartbeat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HeartbeatServer {

    private volatile static boolean isReadyResponse = false;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started: wait for connection");

            Socket socket = serverSocket.accept();
            BufferedReader receivedStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sendStream = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                // Blocked: 如果没有收到信息，ServerSocket会在这里阻塞
                String receivedString = receivedStream.readLine();

                // TODO. 模拟Heartbeat心跳维持客户端的连接，直到服务端处理完数据
                mockHeartbeatThread(sendStream);
                Thread.sleep(25000);

                // 数据处理好之后，客户端必须处于连接状态才能发送回复
                isReadyResponse = true;
                sendStream.println("Send back: " + receivedString);
                sendStream.println("END");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // TODO. 在客户端超时的80%时刻发送Session心跳信号
    private static void mockHeartbeatThread(PrintWriter sendStream) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 等待数据处理完毕
                if (isReadyResponse) {
                    break;
                }
                sendStream.println("heartbeat session!");
            }
        }).start();
    }
}

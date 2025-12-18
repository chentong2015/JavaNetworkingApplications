package test_heartbeat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class HeartbeatClient {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 5000)) {
            socket.setSoTimeout(5000);
            testTimeout(socket);
        } catch (SocketTimeoutException e) {
            System.out.println("Socket Timeout Exception: " + e.getMessage());
        }
    }

    private static void testTimeout(Socket socket) throws IOException {
        System.out.println("Client started: ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string to be echoed: ");
        String input = scanner.nextLine();

        // 发送到服务端的数据
        PrintWriter sendStream = new PrintWriter(socket.getOutputStream(), true);
        sendStream.println(input);

        // TODO. 测试服务端超时返回数据, 收到持续的心跳信号
        BufferedReader receivedStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            String response = receivedStream.readLine();
            if (response.equals("END")) {
                break;
            }
            System.out.println("Received from server: " + response);
        }
    }
}

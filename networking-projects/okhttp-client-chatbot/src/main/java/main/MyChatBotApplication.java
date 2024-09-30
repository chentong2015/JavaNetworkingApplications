package main;

import main.service.HttpClientManager;

import java.io.IOException;
import java.util.Scanner;

public class MyChatBotApplication {

    // Java Console控制台的聊天引用
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("** Input KO to exit Chat Bot. **");
        while (true) {
            System.out.println("Question:");
            String content = scanner.nextLine();
            if (content.equalsIgnoreCase("ko")) {
                break;
            }
            String response = HttpClientManager.sendPostRequest(content);
            System.out.println(response);
        }
        System.out.println("Finish Chat Bot application");
    }
}

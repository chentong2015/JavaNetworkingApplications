package main.controller;

import main.service.HttpClientManager;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Home page";
    }

    // 请求URL localhost:8080/chat?question=hello
    // 允许指定的URL发送跨域认证的请求(Javascript Ajax)
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/chat")
    public String chat(@RequestParam String question) {
        try {
            return HttpClientManager.sendPostRequest(question);
        } catch (IOException exception) {
            exception.printStackTrace();
            return "Service Error: can't get response";
        }
    }
}

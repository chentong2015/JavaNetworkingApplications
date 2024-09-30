package org.openai.chatbot;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/v1/chatbot")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    // 支撑本地Angular Web前端的请求访问
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody MessageRequest messageRequest) {
        ChatResponse response = this.chatbotService.callAzureOpenAi(messageRequest);
        return ResponseEntity.ok(response);
    }
}

package main.model;

import java.util.List;

public class MyRequest {

    private List<Message> messages;

    public MyRequest(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

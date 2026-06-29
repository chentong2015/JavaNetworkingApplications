package main.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.model.MyRequest;
import main.model.MyResponse;

public class JsonHandler {

    public static String convertRequestToJson(MyRequest myRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(myRequest);
    }

    public static MyResponse convertJsonToResponse(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, MyResponse.class);
    }
}

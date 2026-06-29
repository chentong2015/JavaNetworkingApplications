package main.service;

import main.model.Message;
import main.model.MyRequest;
import main.model.MyResponse;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpClientManager {

    private static final String APPLICATION_JSON = "application/json";

    // Access Token valid for 30 days
    private static final String ACCESS_TOKEN = "24.5a5ba017198866af52f204b7b2c1e197.xxx";
    private static final String URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token=" + ACCESS_TOKEN;

    // 使用try-with-resources格式保证client和response都能够被关闭
    public static String sendPostRequest(String content) throws IOException {
        MyRequest myRequest = new MyRequest(List.of(new Message("user", content)));
        RequestBody requestBody = RequestBody.create(JsonHandler.convertRequestToJson(myRequest), MediaType.parse(APPLICATION_JSON));

        Request request = new Request.Builder()
                .url(URL)
                .method("POST", requestBody)
                .addHeader("Content-Type", APPLICATION_JSON)
                .addHeader("Accept", APPLICATION_JSON)
                .build();

        // 设置读取回复的timeout时间，以便获取完整的数据
        OkHttpClient httpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(5, TimeUnit.MINUTES)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseBody = String.valueOf(response.body());
        response.close();

        MyResponse myResponse = JsonHandler.convertJsonToResponse(responseBody);
        return myResponse.getResult();
    }
}

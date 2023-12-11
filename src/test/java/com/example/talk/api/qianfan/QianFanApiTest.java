package com.example.talk.api.qianfan;


import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.example.talk.api.qianfan.model.QianFanQuestionRequest;
import com.example.talk.api.qianfan.model.QianFanQuestionResponse;
import com.example.talk.model.dto.Message;
import com.example.talk.model.dto.Token;
import com.example.talk.model.enums.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/8 16:06
 */
public class QianFanApiTest {

    private final static String ACCESS_TOKEN_URI = "https://aip.baidubce.com/oauth/2.0/token";

    private final static String API_URI = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/qianfan_chinese_llama_2_13b";

    private final static String apiKey = "your api key";

    private final static String secretKey = "your secret key";

    @Test
    public void getTokenTest() throws JsonProcessingException {
        Token token = getToken();
        System.out.println(token);
    }

    private Token getToken() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("grant_type", "client_credentials");
        paramMap.put("client_id", apiKey);
        paramMap.put("client_secret", secretKey);
        String response = HttpRequest.post(ACCESS_TOKEN_URI)
                .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                .header(Header.ACCEPT, ContentType.JSON.getValue())
                .form(paramMap)
                .execute()
                .body();
        return mapper.readValue(response, Token.class);
    }

    @Test
    public void talkTest() throws JsonProcessingException {
        String question = "番茄炒蛋怎么做？";
        Token token = getToken();
        System.out.println("token = " + token.getAccessToken());
        ArrayList<Message> messages = new ArrayList<>();
        Message questionMessage = Message.builder().role(Role.USER.getValue()).content(question).build();
        messages.add(questionMessage);
        QianFanQuestionRequest request = new QianFanQuestionRequest(messages);
        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(request);
        String respJson = HttpRequest.post(API_URI + "?access_token=" + token.getAccessToken())
                .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                .header(Header.ACCEPT, ContentType.JSON.getValue())
                .body(body)
                .execute()
                .body();
        System.out.println("respJson = " + respJson);
        QianFanQuestionResponse response = objectMapper.readValue(respJson, QianFanQuestionResponse.class);
        System.out.println(response);
    }
}
